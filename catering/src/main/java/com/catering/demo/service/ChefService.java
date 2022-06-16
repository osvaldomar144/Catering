package com.catering.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Buffet;
import com.catering.demo.model.Chef;
import com.catering.demo.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional
	public void save(Chef chef) {
		this.chefRepository.save(chef);
	}
	
	@Transactional
	public void delete(Chef chef) {
		this.chefRepository.delete(chef);
	}
	
	@Transactional
	public void update(Chef chef, Long id) {
		Chef c = this.chefRepository.findById(id).get();
		c.setNome(chef.getNome());
		c.setCognome(chef.getCognome());
		c.setNazionalita(chef.getNazionalita());
		c.setBuffets(chef.getBuffets());
		this.chefRepository.save(c);
	}
	
	public Chef findById(Long id) {
		return this.chefRepository.findById(id).get();
	}
	
	public List<Chef> findAll(){
		List<Chef> chefs = new ArrayList<Chef>();
		for(Chef c : this.chefRepository.findAll()) {
			chefs.add(c);
		}	
		return chefs;
	}
	
	
	public boolean alreadyExists(Chef c) {
		return this.chefRepository.existsByNomeAndCognomeAndNazionalita(c.getNome(), 
																        c.getCognome(), 
																        c.getNazionalita());
	}
	
	@Transactional
	public void addBuffet(Long id, Buffet buffet) {
		Chef c = this.chefRepository.findById(id).get();
		buffet.setChef(c);
		c.getBuffets().add(buffet);
		this.chefRepository.save(c);
	}

	public List<Buffet> getBuffetsOfChef(Long id){
		Chef c = this.chefRepository.findById(id).get();
		return c.getBuffets();
	}
	
	//PER HOMEPAGE
	public List<Chef> getUltimiChef(){
		return this.chefRepository.findTop3ByOrderByIdDesc();
	}
}
