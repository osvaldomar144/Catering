package com.catering.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Buffet;
import com.catering.demo.model.Piatto;
import com.catering.demo.repository.BuffetRepository;

@Service
public class BuffetService {
	
	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional
	public void save(Buffet buffet) {
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public void delete(Buffet buffet) {
		buffetRepository.delete(buffet);
	}
	
	@Transactional
	public void update(Buffet buffet, Long id) {
		Buffet b = buffetRepository.findById(id).get();
		b.setNome(buffet.getNome());
		b.setDescrizione(buffet.getDescrizione());
		b.setPiatti(buffet.getPiatti());
		buffetRepository.save(b);
	}
	
	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}
	
	public List<Buffet> findAll(){
		List<Buffet> buffets = new ArrayList<Buffet>();
		for(Buffet c : buffetRepository.findAll()) {
			buffets.add(c);
		}	
		return buffets;
	}

	@Transactional
	public void addPiatto(Long id, Piatto piatto) {
		Buffet buffet = this.buffetRepository.findById(id).get();
		piatto.setBuffet(buffet);
		buffet.getPiatti().add(piatto);
		this.buffetRepository.save(buffet);
	}

	public List<Piatto> getPiattiOfBuffet(Long id){
		Buffet buffet = this.buffetRepository.findById(id).get();
		return buffet.getPiatti();
	}
	
	//PER HOMEPAGE
	public List<Buffet> getUltimiBuffet(){
		return this.buffetRepository.findTop3ByOrderByIdDesc();
	}
	
	public boolean alreadyExists(Buffet b) {
		return buffetRepository.existsByNomeAndDescrizione(b.getNome(), b.getDescrizione());
	}
}
