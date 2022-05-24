package com.catering.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CateringController {
	
	@GetMapping("/")
	public String home() {
		return "homepage.html";
	}
}
