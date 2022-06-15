package com.catering.demo.controller;

import static com.catering.demo.model.Buffet.DIR_ADMIN_PAGES_BUFFET;
import static com.catering.demo.model.Piatto.DIR_FOLDER_IMG;
import static com.catering.demo.model.Piatto.DIR_ADMIN_PAGES_PIATTO;
import static com.catering.demo.model.Piatto.DIR_PAGES_PIATTO;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.catering.demo.controller.validator.PiattoValidator;
import com.catering.demo.model.Buffet;
import com.catering.demo.model.Piatto;
import com.catering.demo.service.BuffetService;
import com.catering.demo.service.PiattoService;
import com.catering.demo.utility.FileStore;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService piattoService;

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private PiattoValidator piattoValidator;
	
	/* GENERIC USER */
	
	@GetMapping("/piatti")
	public String getPiatti(Model model) {
		List<Piatto> piatti = this.piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return DIR_PAGES_PIATTO + "piatti";
	}
	
	@GetMapping("/piatto/{id}")
	public String getSingoloPiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", this.piattoService.findById(id));
		model.addAttribute("ingredienti", this.piattoService.getIngredientiOfPiatto(id));
		return DIR_PAGES_PIATTO + "piatto";
	}
	
	/* ADMIN */

	// -- INSERIMENTO -- //
	@GetMapping("/admin/piatto/aggiungiPiatto/{idBuffet}")
	public String addPiattoPage(@PathVariable("idBuffet") Long idBuffet, Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("idBuffet", idBuffet);
		return DIR_ADMIN_PAGES_PIATTO + "piattoForm";
	}

	@PostMapping("/admin/piatto/aggiungiPiatto/{idBuffet}")
	public String addPiattoToBuffet(@Valid @ModelAttribute("piatto") Piatto piatto,
									BindingResult bindingResult,
									@PathVariable("idBuffet") Long idBuffet,
									@RequestParam("file") MultipartFile file,
									Model model) {

		this.piattoValidator.validate(piatto, bindingResult);
		if(!bindingResult.hasErrors()) {
			piatto.setImg(FileStore.store(file, DIR_FOLDER_IMG));
			this.buffetService.addPiatto(idBuffet, piatto);
			return "redirect:/" + DIR_ADMIN_PAGES_BUFFET + "modificaBuffet/" + idBuffet;
		}
		model.addAttribute("idBuffet", idBuffet);
		return DIR_ADMIN_PAGES_PIATTO + "piattoForm";
	}
	
	// -- MODIFICA -- //
	@GetMapping("/admin/piatto/modificaPiatto/{id}")
	public String selezionaPiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", this.piattoService.findById(id));
		model.addAttribute("ingredienti", this.piattoService.getIngredientiOfPiatto(id));
		return DIR_ADMIN_PAGES_PIATTO + "modificaPiatto";
	}

	@PostMapping("/admin/piatto/modificaPiatto/{idBuffet}/{idPiatto}")
	public String modificaPiatto(@Valid @ModelAttribute("piatto") Piatto piatto,
							   BindingResult bindingResult,
							   @PathVariable("idBuffet") Long idBuffet,
							   @PathVariable("idPiatto") Long idPiatto,
							   Model model) {
		this.piattoValidator.validate(piatto, bindingResult);
		piatto.setId(idPiatto);
		if(!bindingResult.hasErrors()) {
			this.piattoService.update(piatto, piatto.getId());
			return "redirect:/" + DIR_ADMIN_PAGES_BUFFET + "modificaBuffet/" + idBuffet;
		}
		piatto.setBuffet(buffetService.findById(idBuffet));
		model.addAttribute("ingredienti", this.piattoService.getIngredientiOfPiatto(piatto.getId()));
		return DIR_ADMIN_PAGES_PIATTO + "modificaPiatto";
	}
	
	// -- CANCELLAZIONE -- //
	@GetMapping("/admin/piatto/delete/{idBuffet}/{idPiatto}")
	public String deleteBuffet(@PathVariable("idBuffet") Long idBuffet,  
							   @PathVariable("idPiatto") Long idPiatto,  
							   Model model) {
		Piatto piatto = this.piattoService.findById(idPiatto);
		Buffet buffet = this.buffetService.findById(idBuffet);
		
		FileStore.removeImg(DIR_FOLDER_IMG, piatto.getImg());
		piatto.getIngredienti().stream().forEach(ingrediente -> ingrediente.eliminaImmagine());
		
		buffet.getPiatti().remove(piatto);
		this.piattoService.delete(piatto);
		this.buffetService.save(buffet);
		return "redirect:/" + DIR_ADMIN_PAGES_BUFFET + "modificaBuffet/" + idBuffet;
	}


}
