package com.catering.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.catering.demo.service.BuffetService;
import com.catering.demo.service.ChefService;
import com.catering.demo.service.PiattoService;

@Controller
public class CateringController {
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private PiattoService piattoService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("ultimiChef", this.chefService.getUltimiChef());
		model.addAttribute("ultimiBuffet", this.buffetService.getUltimiBuffet());
		model.addAttribute("ultimiPiatti", this.piattoService.getUltimiPiatti());
		return "homepage.html";
	}
	
	@GetMapping("/admin")
	public String adminPage(Model model) {
		return "admin/dashboard";
	}
}
