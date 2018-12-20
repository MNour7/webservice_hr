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
import com.example.nour.repository.CountryRepository;
import com.example.nour.repository.LocationRepository;
import com.example.nour.model.Country;
import com.example.nour.model.Location;

@Controller
@RequestMapping(path="/location")

public class LocationController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@GetMapping(path="/all")
	public String allLocations(Model model) {
		model.addAttribute("locations", locationRepository.findAll());
		
		return "locations";
	}
	
	@GetMapping(path="/edit/{id}")
	public String edit(Model model,@PathVariable Long id) {
		Optional<Location> opt = locationRepository.findById(id);
		Location location = opt.get();	
		model.addAttribute("locationForm", location);
		model.addAttribute("countries", countryRepository.findAll());
		
		return "locationEdit";
	}

	@PostMapping(path="/update")
	public String update(Model model, @ModelAttribute Location loca) {
		
		Optional<Location> opt = locationRepository.findById(loca.getLocationId());
		Country count = countryRepository.findById(loca.getCountry().getCountryId()).get();
		Location location = opt.get();
		
		location.setStreetAddress(loca.getStreetAddress());
		location.setPostalCode(loca.getPostalCode());
		location.setCity(loca.getCity());
		location.setStateProvince(loca.getStateProvince());
		location.setCountry(count);
		
		locationRepository.save(location);
		
		return "redirect:all";
	}
}
