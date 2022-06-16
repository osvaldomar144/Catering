package com.catering.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.catering.demo.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long>{

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);
	
	public List<Buffet> findTop3ByOrderByIdDesc();
}
