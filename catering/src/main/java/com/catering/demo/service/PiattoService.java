package com.catering.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Ingrediente;
import com.catering.demo.model.Piatto;
import com.catering.demo.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	private PiattoRepository piattoRepository;
	
	@Transactional
	public Piatto save(Piatto piatto) {
		return piattoRepository.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) {
		piattoRepository.delete(piatto);
	}
	
	@Transactional
	public void update(Piatto piatto, Long id) {
		Piatto p = piattoRepository.findById(id).get();
		p.setNome(piatto.getNome());
		p.setDescrizione(piatto.getDescrizione());
		p.setIngredienti(piatto.getIngredienti());
		piattoRepository.save(p);
	}
	
	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}
	
	public List<Piatto> findAll(){
		List<Piatto> piatti = new ArrayList<Piatto>();
		for(Piatto c : piattoRepository.findAll()) {
			piatti.add(c);
		}	
		return piatti;
	}
	
	public List<Ingrediente> getIngredientiOfPiatto(Long id){
		Piatto piatto = this.piattoRepository.findById(id).get();
		return piatto.getIngredienti();
	}
	
	public boolean alreadyExists(Piatto p) {
		return piattoRepository.existsByNomeAndDescrizione(p.getNome(), 
														   p.getDescrizione());
	}
}
