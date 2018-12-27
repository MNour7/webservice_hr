package com.example.nour;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/nour")
public class NourController {
	
	@GetMapping(path="/all")
	public String allCountries(Model model) {
		return "jobs";
	}
}
