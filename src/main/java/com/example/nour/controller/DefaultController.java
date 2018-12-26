package com.example.nour.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
	
	@GetMapping("/")
    public String welcome() {
        return "welcome";
    }
	
	@GetMapping("/login")
	public String login() {
	    return "login";
	}
	
	@GetMapping("/home")
	public String home() {
	    return "home";
	}
}
