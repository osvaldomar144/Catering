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

import com.catering.demo.controller.validator.BuffetValidator;
import com.catering.demo.model.Buffet;
import com.catering.demo.model.Chef;
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
	
	@GetMapping("/buffets")
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
	
	// -- INSERIMENTO -- //
	@GetMapping("/admin/buffet/aggiungiBuffet/{idChef}")
	public String addBuffetPage(@PathVariable("idChef") Long idChef, Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("idChef", idChef);
		return DIR_ADMIN_PAGES_BUFFET + "buffetForm";
	}
	
	@PostMapping("/admin/buffet/aggiungiBuffet/{idChef}")
	public String addBuffetToChef(@Valid @ModelAttribute("buffet") Buffet buffet, 
								  @PathVariable("idChef") Long idChef,
			   					  BindingResult bindingResult,
			   					  Model model) {

		this.buffetValidator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.chefService.addBuffet(idChef, buffet);
			return "redirect:/" + DIR_ADMIN_PAGES_CHEF + "modificaChef/" + idChef;
		}
		model.addAttribute("idChef", idChef);
		return DIR_ADMIN_PAGES_BUFFET + "buffetForm";
	}
	
	// -- MODIFICA -- //
	@GetMapping("/admin/buffet/modificaBuffet/{id}")
	public String selezionaBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));
		model.addAttribute("piatti", this.buffetService.getPiattiOfBuffet(id));
		return DIR_ADMIN_PAGES_BUFFET + "modificaBuffet";
	}

	@PostMapping("/admin/buffet/modificaBuffet/{idChef}/{id}")
	public String modificaBuffet(@Valid @ModelAttribute("buffet") Buffet buffet,
							   BindingResult bindingResult,
							   @PathVariable("idChef") Long idChef,
							   @PathVariable("id") Long id,
							   Model model) {
		this.buffetValidator.validate(buffet, bindingResult);
		buffet.setId(id);
		if(!bindingResult.hasErrors()) {
			this.buffetService.update(buffet, buffet.getId());
			return "redirect:/" + DIR_ADMIN_PAGES_CHEF + "modificaChef/" + idChef;
		}
		buffet.setChef(chefService.findById(idChef));
		return DIR_ADMIN_PAGES_BUFFET + "modificaBuffet";
	}
	
	// -- CANCELLAZIONE -- //
	@GetMapping("/admin/buffet/delete/{idChef}/{idBuffet}")
	public String deleteBuffet(@PathVariable("idChef") Long idChef,  
							   @PathVariable("idBuffet") Long idBuffet,  
							   Model model) {
		Buffet buffet = this.buffetService.findById(idBuffet);
		Chef chef = this.chefService.findById(idChef);
		chef.getBuffets().remove(buffet);
		this.buffetService.delete(buffet);
		this.chefService.save(chef);
		return "redirect:/" + DIR_ADMIN_PAGES_CHEF + "modificaChef/" + idChef;
	}
}
