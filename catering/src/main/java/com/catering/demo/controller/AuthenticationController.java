package com.catering.demo.controller;

import static com.catering.demo.model.User.DIR_FOLDER_IMG;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.catering.demo.controller.validator.CredentialsValidator;
import com.catering.demo.controller.validator.UserValidator;
import com.catering.demo.model.Credentials;
import com.catering.demo.model.User;
import com.catering.demo.service.CredentialsService;
import com.catering.demo.service.UserService;
import com.catering.demo.utility.FileStore;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		return this.profileUser(model);

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
			user.setImg("icon-user-default.png");
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "Authentication/registrationSuccessful";
		}
		return "Authentication/registerForm";
		
	}
	
	/* PROFILE */
	@GetMapping("/profile")
	public String profileUser(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = userService.getUser(credentials.getUser().getId());
		model.addAttribute("user", user);
		model.addAttribute("credentials", credentials);
		return "Authentication/profile";
	}
	
	@PostMapping("/changeUserAndPass/{idCred}")
	public String changeUserAndPass(@Valid @ModelAttribute("credentials") Credentials credentials,
									BindingResult credentialsBindingResult,
									@PathVariable("idCred") Long id,
									@RequestParam(name = "confirmPass") String pass,								
									Model model) {
		
		credentials.setUsername("defaultUsernameForVa");
		credentialsValidator.validate(credentials, credentialsBindingResult);
		
		if(!credentials.getPassword().equals(pass)) {
			credentialsBindingResult.addError(new ObjectError("notMatchConfirmPassword", "Le password non coincidono"));
		}
		
		Credentials c = credentialsService.getCredentials(id);
		User user = userService.getUser(c.getUser().getId());
		credentials.setUsername(c.getUsername());
		credentials.setId(id);
		
		if(!credentialsBindingResult.hasErrors()) {
			c.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
			credentialsService.save(c);
			model.addAttribute("user", user);
			model.addAttribute("credentials", c);
			model.addAttribute("okChange", true);
			return "Authentication/profile";
		}	
		model.addAttribute("user", user);
		model.addAttribute("credentials", credentials);
		model.addAttribute("okChange", false);
		return "Authentication/profile";
	}
	
	@PostMapping("/changeImgProfile/{idUser}")
	public String changeImgProfile(@PathVariable("idUser") Long id,
								   @RequestParam("file") MultipartFile file, Model model) {
		User user = userService.getUser(id);
		if(!user.getImg().equals("icon-user-default.png")) {
			FileStore.removeImg(DIR_FOLDER_IMG, user.getImg());
		}
		user.setImg(FileStore.store(file, DIR_FOLDER_IMG));
		userService.saveUser(user);
		return this.profileUser(model);
	}
	
	
	
	
	
}
