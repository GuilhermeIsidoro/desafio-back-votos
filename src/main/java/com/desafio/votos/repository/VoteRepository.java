package com.desafio.votos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.votos.model.Vote;
import com.desafio.votos.projection.VoteCountProjection;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

	List<Vote> findAllByAgendaIdOrderByVotingDate(Long agendaId);

	Optional<Vote> findByAgendaIdAndAssociateId(Long agendaId, String associateCPF);

	@Query(value =	"SELECT 									" + 
					"	VT.vote_description voteDescription, 	" + 
					"	count(V.ID) voteCount 					" + 
					"FROM 										" + 
					"	DESAFIO_VOTOS.VOTE V, 					" + 
					"	DESAFIO_VOTOS.VOTE_TYPE VT 				" + 
					"WHERE 										" + 
					"	VT.ID = V.VOTE_TYPE_ID 					" + 
					"	AND V.AGENDA_ID = :agendaId 			" + 
					"group by VT.vote_description 				" + 
					"order by SUM(V.ID) desc					", nativeQuery = true)
	List<VoteCountProjection> countVotes(@Param("agendaId") Long agendaId);
	
}
