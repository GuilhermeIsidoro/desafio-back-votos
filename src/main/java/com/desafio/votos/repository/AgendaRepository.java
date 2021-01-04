package com.desafio.votos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.votos.model.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

	@Query(value = "SELECT a FROM VotingSession vs JOIN vs.agenda a WHERE :now >= vs.startTime AND :now <= vs.endTime AND vs.agenda.id = a.id")
	List<Agenda> findAgendasWithOpenSessions(@Param("now") LocalDateTime now);

}
