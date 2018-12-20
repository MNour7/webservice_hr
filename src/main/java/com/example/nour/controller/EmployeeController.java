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
	
	@GetMapping(path="/decileSalary")
	public String decileSalary(Model model) {
		List<Employee> emps = (List<Employee>) employeeRepository.findAllByOrderBySalaryAsc();
		ArrayList<BigDecimal> sals = new ArrayList<>();
		ArrayList<BigDecimal> deciles = new ArrayList<>();
		
		for (int i = 0; i < emps.size(); i++) {
			sals.add(emps.get(i).getSalary());
		}
		
		int n = sals.size();
		int indLeft = 0;
		int indRight = 0;
		ArrayList<BigDecimal> interm = new ArrayList<>();
		ArrayList<Double> reparts = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			indRight = Math.round((i*n)/10);
			
			deciles.add(sals.get(indRight));
			//recup taille de la classe
			double portion = sals.subList(indLeft, indRight+1).size();

			//calcul portion de la class dans l'ensemble
			reparts.add((portion/n)*100);
			
			indLeft = indRight+1;
		}
		
		String repartString = "";
		for (int i = 0; i < reparts.size(); i++) {
			if(i < reparts.size()-1)
				repartString += reparts.get(i)+"-";
			else
				repartString += reparts.get(i);
		}
		
		String decileString = "";
		for (int i = 0; i < deciles.size(); i++) {
			if(i < deciles.size()-1)
				decileString += deciles.get(i)+"-";
			else
				decileString += deciles.get(i);
		}
		
		model.addAttribute("deciles", decileString);
		model.addAttribute("reparts", repartString);
		
		return "decileSalary";
	}
	
}
