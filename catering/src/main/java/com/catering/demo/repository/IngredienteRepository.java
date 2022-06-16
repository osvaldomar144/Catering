package com.catering.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.catering.demo.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{

	public boolean existsByNomeAndOrigineAndDescrizione(String nome, 
			                                            String origine,
			                                            String descrizione);
}
