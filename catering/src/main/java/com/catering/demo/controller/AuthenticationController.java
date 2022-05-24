package com.catering.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.catering.demo.model.Credentials;
import com.catering.demo.model.User;
import com.catering.demo.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "Authentication/registerForm";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginForm(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "Authentication/loginForm";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(Model model) {
		return "homepage";
	}
	
	@RequestMapping(value="/default", method=RequestMethod.GET)
	public String defaultAfterLogin(Model model) {
		System.out.println("defaultAfterLogin dentro if");
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getRuolo().equals(Credentials.ADMIN_ROLE)) {
			return "admin/dashboard";
		}
		System.out.println("defaultAfterLogin fuori if");
		return "homepage";

	}
	
	@PostMapping(value= {"/register"})
	public String registerUser(@Valid @ModelAttribute("user") User user,
								BindingResult userBindingResult, 
							   @Valid @ModelAttribute("credentials") Credentials credentials,
							    BindingResult credentialsBindingResult,
							    Model model) {
		System.out.println(user.getNome() + user.getCognome());
		System.out.println(credentials.getUsername() + credentials.getPassword());
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			System.out.println("IN TEORIA E' ENTRATO QUI");
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "Authentication/registrationSuccessful";
		}
		System.out.println(userBindingResult.toString());
		System.out.println(credentialsBindingResult.toString());
		return "Authentication/registerForm";
		
	}
	
}
