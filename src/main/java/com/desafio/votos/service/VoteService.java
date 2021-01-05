package com.desafio.votos.service;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.votos.enumeration.VoteStatusEnum;
import com.desafio.votos.exception.ForbiddenException;
import com.desafio.votos.exception.InternalServerErrorException;
import com.desafio.votos.exception.NotFoundException;
import com.desafio.votos.facade.PersonValidationFacade;
import com.desafio.votos.mapping.VoteCountMapper;
import com.desafio.votos.mapping.VoteMapper;
import com.desafio.votos.model.Vote;
import com.desafio.votos.model.VotingSession;
import com.desafio.votos.model.dto.AgendaDTO;
import com.desafio.votos.model.dto.AgendaVotingResultDTO;
import com.desafio.votos.model.dto.AssociateDTO;
import com.desafio.votos.model.dto.PersonStatusDTO;
import com.desafio.votos.model.dto.VoteCountDTO;
import com.desafio.votos.model.dto.VoteDTO;
import com.desafio.votos.model.dto.VoteTypeDTO;
import com.desafio.votos.model.dto.VotingDTO;
import com.desafio.votos.model.dto.VotingSessionDTO;
import com.desafio.votos.repository.VoteRepository;

@Service
public class VoteService {
	
	Logger logger = LoggerFactory.getLogger(VoteService.class);
	
	private final VoteRepository voteRepository;
	
	private final VoteMapper voteMapper;
	
	private final PersonValidationFacade personValidationFacade;
	
	private final AgendaService agendaService;
	
	private final VotingSessionService votingSessionService;
	
	private final VoteTypeService voteTypeService;
	
	private final AssociateService associateService;
	
	@Autowired
	private VoteCountMapper voteCountMapper;
	
	public VoteService(VoteRepository voteRepository,
					   VoteMapper voteMapper,
					   PersonValidationFacade personValidationFacade,
					   AgendaService agendaService,
					   VotingSessionService votingSessionService,
					   VoteTypeService voteTypeService,
					   AssociateService associateService) {
		this.voteRepository = voteRepository;
		this.voteMapper = voteMapper;
		this.personValidationFacade = personValidationFacade;
		this.agendaService = agendaService;
		this.votingSessionService = votingSessionService;
		this.voteTypeService = voteTypeService;
		this.associateService = associateService;
	}

	public List<VoteDTO> findVotesByAgenda(Long agendaId) {
		logger.debug("SEARCHING VOTES FOR AGENDA: {}", agendaId);
		return voteRepository.findAllByAgendaIdOrderByVotingDate(agendaId).stream().map(voteMapper::toDTO).collect(Collectors.toList());
	}

	public Optional<VoteDTO> findVoteById(Long voteId) {
		logger.debug("SEARCHING FOR VOTE: {}", voteId);
		return voteRepository.findById(voteId).map(voteMapper::toDTO);
	}
	
	private Optional<Vote> findByAssociateVoteOnAgenda(Long agendaId, String associateCPF) {
		logger.debug("SEARCHING ASSOCIATE VOTE FOR AGENDA: {}", agendaId);
		return voteRepository.findByAgendaIdAndAssociateId(agendaId, associateCPF);
	}

	public VoteDTO vote(@Valid VotingDTO voting) throws URISyntaxException, NotFoundException, InternalServerErrorException, ForbiddenException {
		
		logger.debug("CREATING NEW VOTE FOR AGENDA: {}, VOTING SESSION: {}", voting.getAgendaId(), voting.getVotingSessionId());
		
		PersonStatusDTO personStatus = personValidationFacade.validatePerson(voting.getAssociateCPF());
		
		if(personStatus.getStatus().equalsIgnoreCase(VoteStatusEnum.UNABLE_TO_VOTE.status())) {
			throw new ForbiddenException("Associado inapto para votação");
		}
		
		VotingSessionDTO votingSession = validateEntities(voting);
		
		validateSession(votingSession);
		
		validateDuplicateVote(voting.getAgendaId(), voting.getAssociateCPF());
		
		Vote vote = voteMapper.toEntity(voting);
		
		vote.setVotingDate(LocalDateTime.now());
		
		Vote createdVote = voteRepository.save(vote);
		
		return voteMapper.toDTO(createdVote);
	}
	
	private void validateSession(@Valid VotingSessionDTO votingSession) throws ForbiddenException {
		
		logger.debug("VALIDATING SESSION {}", votingSession);
		
		LocalDateTime now = LocalDateTime.now();
		
		if(!VotingSession.isOpen(now, votingSession.getStartTime(), votingSession.getEndTime())) {
			throw new ForbiddenException("Sessão de votação fechada");
		}
	}

	private VotingSessionDTO validateEntities(VotingDTO voting) throws NotFoundException {
		
		Optional<AgendaDTO> agenda = agendaService.find(voting.getAgendaId());
		
		if(!agenda.isPresent()) {
			throw new NotFoundException("Pauta informada não encontrada");
		}
		
		Optional<VoteTypeDTO> voteType = voteTypeService.findVoteType(voting.getVoteTypeId());
		
		if(!voteType.isPresent()) {
			throw new NotFoundException("Tipo de voto informado não encontrado");
		}
		
		Optional<VotingSessionDTO> votingSession = votingSessionService.find(voting.getVotingSessionId());
		
		if(!votingSession.isPresent()) {
			throw new NotFoundException("Sessão informada não encontrada");
		}
		
		Optional<AssociateDTO> associate = associateService.findAssociate(voting.getAssociateCPF());
		
		if(!associate.isPresent()) {
			throw new NotFoundException("Associado não encontrado");
		}
		
		return votingSession.get();
	}
	
	private void validateDuplicateVote(Long agendaId, String associateCPF) throws ForbiddenException {
		
		logger.debug("SEARCHING FOR DUPLICATE VOTES");
		
		Optional<Vote> potentialVote = findByAssociateVoteOnAgenda(agendaId, associateCPF);
		
		if(potentialVote.isPresent()) {
			throw new ForbiddenException("Não é permitido mais de um voto de associado por Pauta");
		}
	}

	public AgendaVotingResultDTO checkResult(Long agendaId) throws NotFoundException {
		
		logger.debug("CHECKING FOR AGENDA {} RESULT", agendaId);
		
		Optional<AgendaDTO> agenda = agendaService.find(agendaId);
		
		if(!agenda.isPresent()) {
			throw new NotFoundException("Pauta informada não foi encontrada");
		}
		
		List<VoteCountDTO> voteCounts = voteRepository.countVotes(agendaId).stream().map(voteCountMapper::toDTO).collect(Collectors.toList());
		
		VoteCountDTO winner = null; 
		
		if(!voteCounts.isEmpty()) {
			winner = voteCounts.get(0);
		}
		
		return new AgendaVotingResultDTO(agenda.get(), voteCounts, winner);
	}

}
