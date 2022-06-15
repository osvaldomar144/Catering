package com.catering.demo.controller;

import static com.catering.demo.model.Ingrediente.DIR_FOLDER_IMG;
import static com.catering.demo.model.Ingrediente.DIR_ADMIN_PAGES_INGREDIENTE;
import static com.catering.demo.model.Ingrediente.DIR_PAGES_INGREDIENTE;
import static com.catering.demo.model.Piatto.DIR_ADMIN_PAGES_PIATTO;

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

import com.catering.demo.model.Ingrediente;
import com.catering.demo.model.Piatto;
import com.catering.demo.service.IngredienteService;
import com.catering.demo.service.PiattoService;
import com.catering.demo.utility.FileStore;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private PiattoService piattoService;

	/*@Autowired
	private IngredienteValidator ingredienteValidator;*/
	
	/* GENERIC USER */
	
	@GetMapping("/ingredienti")
	public String getIngredienti(Model model) {
		List<Ingrediente> ingredienti = this.ingredienteService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		return DIR_PAGES_INGREDIENTE + "ingredienti";
	}
	
	@GetMapping("/ingrediente/{id}")
	public String getSingoloIngrediente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingrediente", this.ingredienteService.findById(id));
		return DIR_PAGES_INGREDIENTE + "ingrediente";
	}
	
	/* ADMIN */

	// -- INSERIMENTO -- //
	@GetMapping("/admin/ingrediente/aggiungiIngrediente/{idPiatto}")
	public String addIngredientePage(@PathVariable("idPiatto") Long idPiatto, Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		model.addAttribute("idPiatto", idPiatto);
		return DIR_ADMIN_PAGES_INGREDIENTE + "ingredienteForm";
	}

	@PostMapping("/admin/ingrediente/aggiungiIngrediente/{idPiatto}")
	public String addIngredienteToPiatto(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
										 BindingResult bindingResult,
										 @PathVariable("idPiatto") Long idPiatto,
										 @RequestParam("file") MultipartFile file,
										 Model model) {

		//this.ingredienteValidator.validate(ingrediente, bindingResult);
		if(!bindingResult.hasErrors()) {
			ingrediente.setImg(FileStore.store(file, DIR_FOLDER_IMG));
			this.piattoService.addIngrediente(idPiatto, ingrediente);
			return "redirect:/" + DIR_ADMIN_PAGES_PIATTO + "modificaPiatto/" + idPiatto;
		}
		model.addAttribute("idPiatto", idPiatto);
		return DIR_ADMIN_PAGES_INGREDIENTE + "ingredienteForm";
	}
	
	// -- MODIFICA -- //
	@GetMapping("/admin/ingrediente/modificaIngrediente/{idPiatto}/{idIngrediente}")
	public String selezionaIngrediente(@PathVariable("idPiatto") Long idPiatto, 
									   @PathVariable("idIngrediente") Long idIngrediente, 
			                           Model model) {
		model.addAttribute("ingrediente", this.ingredienteService.findById(idIngrediente));
		model.addAttribute("idPiatto", idPiatto);
		return DIR_ADMIN_PAGES_INGREDIENTE + "modificaIngrediente";
	}

	@PostMapping("/admin/ingrediente/modificaIngrediente/{idPiatto}/{idIngrediente}")
	public String modificaIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,
							   BindingResult bindingResult,
							   @PathVariable("idPiatto") Long idPiatto,
							   @PathVariable("idIngrediente") Long idIngrediente,
							   Model model) {
		//this.ingredienteValidator.validate(ingrediente, bindingResult);
		ingrediente.setId(idIngrediente);
		if(!bindingResult.hasErrors()) {
			this.ingredienteService.update(ingrediente, ingrediente.getId());
			return "redirect:/" + DIR_ADMIN_PAGES_PIATTO + "modificaPiatto/" + idPiatto;
		}
		//ingrediente.setPiatto(this.piattoService.findById(idPiatto));
		return DIR_ADMIN_PAGES_INGREDIENTE + "modificaIngrediente";
	}
	
	// -- CANCELLAZIONE -- //
	@GetMapping("/admin/ingrediente/delete/{idPiatto}/{idIngrediente}")
	public String deleteBuffet(@PathVariable("idPiatto") Long idPiatto,  
							   @PathVariable("idIngrediente") Long idIngrediente,  
							   Model model) {
		Ingrediente ingrediente = this.ingredienteService.findById(idIngrediente);
		Piatto piatto = this.piattoService.findById(idPiatto);
		FileStore.removeImg(DIR_FOLDER_IMG, ingrediente.getImg());
		piatto.getIngredienti().remove(ingrediente);
		this.ingredienteService.delete(ingrediente);
		this.piattoService.save(piatto);
		return "redirect:/" + DIR_ADMIN_PAGES_PIATTO + "modificaPiatto/" + idPiatto;
	}

}
