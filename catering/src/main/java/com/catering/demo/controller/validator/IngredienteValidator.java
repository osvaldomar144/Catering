package com.catering.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.demo.model.Ingrediente;
import com.catering.demo.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator {

	@Autowired
	private IngredienteService ingredienteService;
	
    @Override
    public void validate(Object o, Errors errors) {
        Ingrediente ingrediente = (Ingrediente)o;

        if (this.ingredienteService.alreadyExists(ingrediente)) {
            errors.reject("duplicate.ingrediente");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Ingrediente.class.equals(clazz);
    }
    
}
