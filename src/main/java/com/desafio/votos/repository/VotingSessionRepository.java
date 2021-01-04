package com.desafio.votos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.votos.model.VotingSession;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {

	@Query(value = "SELECT VS FROM VotingSession VS WHERE :now >= VS.startTime AND :now <= VS.endTime")
	List<VotingSession> findOpenSessions(@Param("now") LocalDateTime now);

}
