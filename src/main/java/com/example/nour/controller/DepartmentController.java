package com.example.nour.controller;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nour.repository.DepartmentRepository;
import com.example.nour.repository.EmployeeRepository;
import com.example.nour.repository.LocationRepository;
import com.example.nour.model.Department;
import com.example.nour.model.DepartmentDTO;
import com.example.nour.model.Employee;
import com.example.nour.model.EmployeeDTO;

@Controller
@RequestMapping(path="/department")
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	
	@GetMapping(path="/all")
	public String allDepartments(Model model) {
		model.addAttribute("departments", departmentRepository.findAll());
		
		return "departments";
	}
	
	@GetMapping(path="/all/dto")
	public @ResponseBody List<DepartmentDTO> allDepDTOs(){
		
		List<DepartmentDTO> depDTOs = new ArrayList<>();
		List<Department> deps = (List<Department>) departmentRepository.findAll();
		List<Employee> emps = (List<Employee>) employeeRepository.findAll();
		
		for(int i = 0; i < deps.size(); i++) {
			DepartmentDTO depDTO = modelMapper.map(deps.get(i), DepartmentDTO.class);
			depDTO.setDepartmentId(deps.get(i).getDepartmentId());
			depDTO.setDepartmentName(deps.get(i).getDepartmentName());
			depDTO.setLocation(deps.get(i).getLocation().getCity());
			/*
			
			int indiceManager = 0;
			for(int j = 0; j< emps.size();j++) {
				if(deps.get(i).getManagerId()==emps.get(j).getManagerId()) {
					indiceManager=j;
				}
			}
			depDTO.setManagerName(
					emps.get(
							(int)deps.get(indiceManager).getManagerId().intValueExact()
							).getFirstName()
					);
			
			*/
			
			depDTOs.add(depDTO);
			
		}
		
		return depDTOs;
	}
}
