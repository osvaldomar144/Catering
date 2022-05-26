package com.catering.demo.controller;

import static com.catering.demo.model.Buffet.DIR_ADMIN_PAGES_BUFFET;
import static com.catering.demo.model.Buffet.DIR_PAGES_BUFFET;
import static com.catering.demo.model.Chef.DIR_ADMIN_PAGES_CHEF;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.catering.demo.controller.validator.BuffetValidator;
import com.catering.demo.model.Buffet;
import com.catering.demo.service.BuffetService;
import com.catering.demo.service.ChefService;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetValidator buffetValidator;
	
	/* GENERIC USER */
	
	@GetMapping("/buffet")
	public String getBuffets(Model model) {
		List<Buffet> buffets = this.buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return DIR_PAGES_BUFFET + "buffets";
	}
	
	@GetMapping("/buffet/{id}")
	public String getSingoloBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));
		return DIR_PAGES_BUFFET + "buffet";
	}
	
	/* ADMIN */
	
	@GetMapping("/admin/buffet/aggiungiBuffet/{idChef}")
	public String addBuffetPage(@PathVariable("idChef") Long idChef, Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("idChef", idChef);
		return DIR_ADMIN_PAGES_BUFFET + "buffetForm";
	}
	
	@PostMapping("/admin/buffet/aggiungiBuffet/{idChef}")
	public ModelAndView addBuffetToChef(@Valid @ModelAttribute("buffet") Buffet buffet, 
								  @PathVariable("idChef") Long idChef,
			   					  BindingResult bindingResult,
			   					  Model model) {

		this.buffetValidator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.chefService.addBuffet(idChef, buffet);
			return new ModelAndView("redirect:/" + DIR_ADMIN_PAGES_CHEF + "modificaChef/" + idChef);
		}
		return new ModelAndView("redirect:/" + DIR_ADMIN_PAGES_BUFFET + "aggiungiBuffet/" + idChef);
	}
	
	@GetMapping("/admin/buffet/modificaBuffet/{id}")
	public String selezionaBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));
		return DIR_ADMIN_PAGES_BUFFET + "modificaBuffet";
	}
}
