package com.catering.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.demo.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long>{

	public boolean existsByNomeAndDescrizioneAndChef(String nome, String descrizione, Long idChef);
}
