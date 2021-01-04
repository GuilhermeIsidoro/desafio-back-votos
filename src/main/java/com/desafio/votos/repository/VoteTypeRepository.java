package com.desafio.votos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.votos.model.VoteType;

@Repository
public interface VoteTypeRepository extends JpaRepository<VoteType, Long> {

	List<VoteType> findAllByAgendaIdOrderByVoteDescription(Long agendaId);

}
