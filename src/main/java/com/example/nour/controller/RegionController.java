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

import com.example.nour.model.Job;
import com.example.nour.model.Region;
import com.example.nour.repository.RegionRepository;

@Controller
@RequestMapping(path="/region")
public class RegionController {
	
	@Autowired
	private RegionRepository regionRepository;
	
	@GetMapping(path="/all")
	public String allRegions(Model model) {
		model.addAttribute("regions", regionRepository.findAll());
		
		return "regions";
	}
	
	@GetMapping(path="/edit/{id}")
	public String edit(Model model,@PathVariable Long id) {
		Optional<Region> opt = regionRepository.findById(id);
		Region region = opt.get();	
		model.addAttribute("regionForm", region);	
		
		return "regionEdit";
	}
	
	@PostMapping(path="/update")
	public String update(Model model, @ModelAttribute Region reg) {
		
		Optional<Region> opt =regionRepository.findById(reg.getRegionId());
		
		Region region = opt.get();
		region.setRegionName(reg.getRegionName());
		
		regionRepository.save(region);
		
		return "redirect:all";
	}

}
