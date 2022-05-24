package com.catering.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Chef;
import com.catering.demo.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional
	public void save(Chef chef) {
		chefRepository.save(chef);
	}
	
	@Transactional
	public void delete(Chef chef) {
		chefRepository.delete(chef);
	}
	
	@Transactional
	public void update(Chef chef) {
		Chef c = chefRepository.findById(chef.getId()).get();
		c.setNome(chef.getNome());
		c.setCognome(chef.getCognome());
		c.setNazionalita(chef.getNazionalita());
		c.setBuffets(chef.getBuffets());
		chefRepository.save(c);
	}
	
	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}
	
	public List<Chef> findAll(){
		List<Chef> chefs = new ArrayList<Chef>();
		for(Chef c : chefRepository.findAll()) {
			chefs.add(c);
		}	
		return chefs;
	}
	
	public boolean alreadyExists(Chef c) {
		return chefRepository.existsByNomeAndCognomeAndNazionalita(c.getNome(), 
																   c.getCognome(), 
																   c.getNazionalita());
	}
}
