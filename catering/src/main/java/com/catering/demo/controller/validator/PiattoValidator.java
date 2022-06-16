package com.catering.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.catering.demo.model.Piatto;
import com.catering.demo.service.PiattoService;

@Component
public class PiattoValidator implements Validator {

    @Autowired
    private PiattoService piattoService;

    @Override
    public void validate(Object o, Errors errors) {
        Piatto piatto = (Piatto)o;

        if (this.piattoService.alreadyExists(piatto)) {
            errors.reject("duplicate.piatto");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Piatto.class.equals(clazz);
    }
}
