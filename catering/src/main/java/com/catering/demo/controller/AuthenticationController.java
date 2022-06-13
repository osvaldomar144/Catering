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

import com.catering.demo.controller.validator.CredentialsValidator;
import com.catering.demo.controller.validator.UserValidator;
import com.catering.demo.model.Credentials;
import com.catering.demo.model.User;
import com.catering.demo.service.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "Authentication/registerForm";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginForm(Model model) {
		return "Authentication/loginForm";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(Model model) {
		return "homepage";
	}
	
	@RequestMapping(value="/default", method=RequestMethod.GET)
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getRuolo().equals(Credentials.ADMIN_ROLE)) {
			return "admin/dashboard";
		}
		return "homepage";

	}
	
	@PostMapping(value= {"/register"})
	public String registerUser(@Valid @ModelAttribute("user") User user,
								BindingResult userBindingResult, 
							   @Valid @ModelAttribute("credentials") Credentials credentials,
							    BindingResult credentialsBindingResult,
							    Model model) {
		
        // validazione user e credenziali
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "Authentication/registrationSuccessful";
		}
		return "Authentication/registerForm";
		
	}
	
	/* PROFILE */
	
	
}
