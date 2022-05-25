package com.catering.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Piatto;
import com.catering.demo.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	private PiattoRepository piattoRepository;
	
	@Transactional
	public void save(Piatto piatto) {
		piattoRepository.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) {
		piattoRepository.delete(piatto);
	}
	
	@Transactional
	public void update(Piatto piatto) {
		Piatto p = piattoRepository.findById(piatto.getId()).get();
		p.setNome(piatto.getNome());
		p.setDescrizione(piatto.getDescrizione());
		p.setBuffets(piatto.getBuffets());
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
	
	public boolean alreadyExists(Piatto p) {
		return piattoRepository.existsByNomeAndDescrizione(p.getNome(), 
														   p.getDescrizione());
	}
}
