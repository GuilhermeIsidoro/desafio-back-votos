package com.desafio.votos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.votos.model.Associate;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, String> {

	List<Associate> findAllByOrderByNameAsc();

}
