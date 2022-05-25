package com.catering.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.demo.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long>{

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);
}
