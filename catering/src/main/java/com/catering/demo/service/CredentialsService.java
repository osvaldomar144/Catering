package com.catering.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.Credentials;
import com.catering.demo.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;
	
	public Credentials getCredentials(Long id) {
		return credentialsRepository.findById(id).get();
	}
	
	public Credentials getCredentials(String username) {
		return credentialsRepository.findByUsername(username).get();
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		if (credentials.getRuolo() == null) {
			credentials.setRuolo(Credentials.GENERIC_USER_ROLE);
		}
		return credentialsRepository.save(credentials);
	}
}
