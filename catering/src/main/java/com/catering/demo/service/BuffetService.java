package com.catering.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Buffet;
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
	public void update(Buffet buffet) {
		Buffet b = buffetRepository.findById(buffet.getId()).get();
		b.setNome(buffet.getNome());
		b.setDescrizione(buffet.getDescrizione());
		b.setChef(buffet.getChef());
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
	
	public boolean alreadyExists(Buffet b) {
		return buffetRepository.existsByNomeAndDescrizioneAndChef(b.getNome(), 
																  b.getDescrizione(), 
																  b.getChef().getId());
	}
}
