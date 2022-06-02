package com.catering.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Ingrediente;
import com.catering.demo.repository.IngredienteRepository;

@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Transactional
	public Ingrediente save(Ingrediente ingrediente) {
		return ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) {
		ingredienteRepository.delete(ingrediente);
	}
	
	@Transactional
	public void update(Ingrediente ingrediente, Long id) {
		Ingrediente i = ingredienteRepository.findById(id).get();
		i.setNome(ingrediente.getNome());
		i.setOrigine(ingrediente.getOrigine());
		i.setDescrizione(ingrediente.getDescrizione());
		ingredienteRepository.save(i);
	}
	
	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}
	
	public List<Ingrediente> findAll(){
		List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();
		for(Ingrediente i : ingredienteRepository.findAll()) {
			ingredienti.add(i);
		}	
		return ingredienti;
	}
	
	public boolean alreadyExists(Ingrediente i) {
		return ingredienteRepository.existsByNomeAndOrigineAndDescrizione(i.getNome(),
														   i.getOrigine(),
														   i.getDescrizione());
	}
	
}
