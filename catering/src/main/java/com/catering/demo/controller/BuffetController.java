package com.catering.demo.controller;

import static com.catering.demo.model.Buffet.DIR_PAGES_BUFFET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.catering.demo.model.Buffet;
import com.catering.demo.service.BuffetService;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService buffetService;
	
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
}
