package com.catering.demo.controller;

import static com.catering.demo.model.Piatto.DIR_PAGES_PIATTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.catering.demo.model.Piatto;
import com.catering.demo.service.PiattoService;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;
	
	/* GENERIC USER */
	
	@GetMapping("/piatto")
	public String getPiatti(Model model) {
		List<Piatto> piatti = this.piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return DIR_PAGES_PIATTO + "piatti";
	}
	
	@GetMapping("/piattos/{id}")
	public String getSingoloPiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", this.piattoService.findById(id));
		return DIR_PAGES_PIATTO + "piatto";
	}
	
	/* ADMIN */
}
