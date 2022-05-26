package com.catering.demo.controller;

import static com.catering.demo.model.Chef.DIR_ADMIN_PAGES_CHEF;
import static com.catering.demo.model.Chef.DIR_PAGES_CHEF;

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

import com.catering.demo.controller.validator.ChefValidator;
import com.catering.demo.model.Buffet;
import com.catering.demo.model.Chef;
import com.catering.demo.service.BuffetService;
import com.catering.demo.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefValidator chefValidator;
	
	/* GENERIC USER */
	
	@GetMapping("/chef")
	public String getChefs(Model model) {
		List<Chef> chefs = this.chefService.findAll();
		model.addAttribute("chefs", chefs);
		return DIR_PAGES_CHEF + "chefs";
	}
	
	@GetMapping("/chef/{id}")
	public String getSingoloChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return DIR_PAGES_CHEF + "chef";
	}
	
	/* ADMIN */
	// -- elenco chefs
	@GetMapping("/admin/chef")
	public String adminChefs(Model model) {
		List<Chef> chefs = this.chefService.findAll();
		model.addAttribute("chefs", chefs);
		return DIR_ADMIN_PAGES_CHEF + "adminChef";
	}
	
	// -- page con form per addChef
	@GetMapping("/admin/chef/aggiungiChef")
	public String addChef(Model model) {
		model.addAttribute("chef", new Chef());
		return DIR_ADMIN_PAGES_CHEF + "formChef";
	}
	
	@PostMapping("/admin/chef/aggiungiChef")
	public String aggiungiChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult,
							   Model model) {
		this.chefValidator.validate(chef, bindingResult);	
		if(!bindingResult.hasErrors()) {
			this.chefService.save(chef);
			return this.adminChefs(model);
		}
		return DIR_ADMIN_PAGES_CHEF + "formChef";
	}
	
	@GetMapping("/admin/chef/delete/{id}")
	public String deleteChef(@PathVariable("id") Long id,  Model model) {
		Chef chef = this.chefService.findById(id);
		this.chefService.delete(chef);		
		return this.adminChefs(model);
	}
	
	@GetMapping("/admin/chef/modificaChef/{id}")
	public String selezionaChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return DIR_ADMIN_PAGES_CHEF + "modificaChef";
	}
	
	@PostMapping("/admin/chef/modificaChef/{id}")
	public String modificaChef(@Valid @ModelAttribute("chef") Chef chef, 
							   BindingResult bindingResult,
							   @PathVariable("id") Long id,
							   Model model) {
		this.chefValidator.validate(chef, bindingResult);	
		if(!bindingResult.hasErrors()) {
			this.chefService.update(chef, id);
			return this.adminChefs(model);
		}
		return DIR_ADMIN_PAGES_CHEF + "modificaChef";
	}
	
	// -- gestione buffet per chef
	
	@GetMapping("/admin/chef/deleteBuffet/{idChef}/{idBuff}")
	public String deleteBuffetFromList(@PathVariable("idChef") Long idChef,
									   @PathVariable("idBuff") Long idBuff,
									   Model model) {
		Chef chef = this.chefService.findById(idChef);
		Buffet buffet = this.buffetService.findById(idBuff);
		chef.getBuffets().remove(buffet);
		this.buffetService.delete(buffet);
		this.chefService.save(chef);
		return this.selezionaChef(idChef, model);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
