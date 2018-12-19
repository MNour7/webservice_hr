package com.example.nour.controller;

import java.math.BigDecimal;
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

import com.example.nour.model.Employee;
import com.example.nour.model.EmployeeDTO;
import com.example.nour.repository.EmployeeRepository;

@Controller
@RequestMapping(path="/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping(path="/firstname/{name}")
	public @ResponseBody List<EmployeeDTO> firstName(@PathVariable String name){
		
		List<EmployeeDTO> empDTOs = new ArrayList<>();
		List<Employee> emps = employeeRepository.findAllByFirstName(name);
		
		for(int i = 0; i < emps.size(); i++) {
			EmployeeDTO empDTO = modelMapper.map(emps.get(i), EmployeeDTO.class);
			empDTO.setDepartmentName(emps.get(i).getDepartment().getDepartmentName());
			
			empDTOs.add(empDTO);
		}
		
		return empDTOs;
	}
	
	@GetMapping(path="/plotSalaryPerEmp")
	public String plotSalaryPerEmp(Model model) {
		List<Employee> emps = (List<Employee>) employeeRepository.findAll();
		ArrayList<BigDecimal> sals = new ArrayList<>();
		ArrayList<Long> empIds = new ArrayList<>();
		
		for (int i = 0; i < emps.size(); i++) {
			sals.add(emps.get(i).getSalary());
			empIds.add(emps.get(i).getEmployeeId());
		}
		
		model.addAttribute("sals", sals);
		model.addAttribute("ids", empIds);
		
		return "plotSalaryPerEmp";
	}
	
}
