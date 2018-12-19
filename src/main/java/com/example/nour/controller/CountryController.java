package com.example.nour.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.nour.model.Country;
import com.example.nour.model.CountryDTO;
import com.example.nour.repository.CountryRepository;

@Controller
@RequestMapping(path="/country")
public class CountryController {

	@Autowired
	private CountryRepository countryRepository;
	private ModelMapper modelMapper;
	
	@GetMapping(path="/edit/{id}")
	public String edit(Model model,@PathVariable String id) {
		Optional<Country> opt = countryRepository.findById(id);
		
		Country country = opt.get();
//		System.err.println("NAme : "+country.getCountryName());
//		CountryDTO countryDTO =   modelMapper.map(country, CountryDTO.class);
		
		model.addAttribute("countryForm", country);
		
		
		return "countryEdit";
	}
	
	@PostMapping(path="/update")
	public String update(Model model, @ModelAttribute Country count) {
		
		Optional<Country> opt = countryRepository.findById(count.getCountryId());
		
		Country country = opt.get();
		country.setCountryName(count.getCountryName());
		
		countryRepository.save(country);
		
		return "redirect:all";
	}
	
	@GetMapping(path="/all")
	public String allCountries(Model model) {
		model.addAttribute("countries", countryRepository.findAll());
		
		return "countries";
	}
}
