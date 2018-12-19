package com.example.nour.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.nour.model.Country;
import com.example.nour.model.Region;
import com.example.nour.repository.CountryRepository;
import com.example.nour.repository.RegionRepository;

@Controller
@RequestMapping(path="/country")
public class CountryController {

	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@GetMapping(path="/all")
	public String allCountries(Model model) {
		model.addAttribute("countries", countryRepository.findAll());
		
		return "countries";
	}
	
	@GetMapping(path="/edit/{id}")
	public String edit(Model model,@PathVariable String id) {
		Optional<Country> opt = countryRepository.findById(id);
		Country country = opt.get();	
		model.addAttribute("countryForm", country);
		model.addAttribute("regions", regionRepository.findAll());
		
		return "countryEdit";
	}
	
	@PostMapping(path="/update")
	public String update(Model model, @ModelAttribute Country count) {
		
		Optional<Country> opt = countryRepository.findById(count.getCountryId());
		Region reg = regionRepository.findById(count.getRegion().getRegionId()).get();
		Country country = opt.get();
		country.setCountryName(count.getCountryName());
		country.setRegion(reg);
		countryRepository.save(country);
		
		return "redirect:all";
	}
	

}
