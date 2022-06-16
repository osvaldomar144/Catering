package com.catering.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.demo.model.Chef;
import com.catering.demo.service.ChefService;

@Component
public class ChefValidator implements Validator{

	@Autowired
	private ChefService chefService;
	
	@Override
    public void validate(Object o, Errors errors) {
		Chef chef = (Chef)o;
		
		if (this.chefService.alreadyExists(chef)) {
			errors.reject("duplicate.chef");
		}
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return Chef.class.equals(clazz);
    }
}
