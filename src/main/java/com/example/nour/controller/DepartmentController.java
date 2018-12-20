package com.example.nour.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import com.example.nour.repository.DepartmentRepository;
import com.example.nour.repository.LocationRepository;
import com.example.nour.model.Department;

@Controller
@RequestMapping(path="/department")
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping(path="/all")
	public String allDepartments(Model model) {
		model.addAttribute("departments", departmentRepository.findAll());
		
		return "departments";
	}
}
