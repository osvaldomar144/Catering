package com.catering.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catering.demo.model.User;
import com.catering.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User getUser(Long id) {
		return userRepository.findById(id).get();
	}
	
	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
