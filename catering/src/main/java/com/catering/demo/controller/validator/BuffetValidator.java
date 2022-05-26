package com.catering.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.demo.model.Buffet;
import com.catering.demo.model.Chef;
import com.catering.demo.service.BuffetService;

@Component
public class BuffetValidator implements Validator{

	@Autowired
	private BuffetService buffetService;
	
	@Override
    public void validate(Object o, Errors errors) {
		Buffet buffet = (Buffet)o;
		
		if (this.buffetService.alreadyExists(buffet)) {
			errors.reject("duplicate.buffet");
		}
	}
	
    @Override
    public boolean supports(Class<?> clazz) {
        return Chef.class.equals(clazz);
    }
}
