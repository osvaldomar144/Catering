package com.catering.demo.controller;

import static com.catering.demo.model.Chef.DIR_PAGES_CHEF;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.catering.demo.model.Chef;
import com.catering.demo.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;
	
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
	
}
