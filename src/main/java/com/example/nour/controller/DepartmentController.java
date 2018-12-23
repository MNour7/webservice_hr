package com.example.nour.controller;


import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nour.repository.DepartmentRepository;
import com.example.nour.repository.EmployeeRepository;
import com.example.nour.repository.LocationRepository;
import com.example.nour.model.Country;
import com.example.nour.model.Department;
import com.example.nour.model.DepartmentDTO;
import com.example.nour.model.Employee;
import com.example.nour.model.EmployeeDTO;
import com.example.nour.model.Location;
import com.example.nour.model.Region;

@Controller
@RequestMapping(path="/department")
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	
	public List<DepartmentDTO> getAllDepDTOs(){
		
		List<DepartmentDTO> depDTOs = new ArrayList<>();
		List<Department> deps = (List<Department>) departmentRepository.findAll();
		
		for(int i = 0; i < deps.size(); i++) {
			DepartmentDTO depDTO = modelMapper.map(deps.get(i), DepartmentDTO.class);
			depDTO.setDepartmentId(deps.get(i).getDepartmentId());
			depDTO.setDepartmentName(deps.get(i).getDepartmentName());
			depDTO.setLocation(deps.get(i).getLocation());
			try {
			Employee emp = employeeRepository.findById(deps.get(i).getManagerId().longValue()).get();
			depDTO.setManagerName(
					emp.getFirstName() + " " + emp.getLastName()
					);
			}catch(Exception e){
				depDTO.setManagerName(
						"Unknown Manager"
						);
			}
			
			depDTOs.add(depDTO);
			
		}
		
		return depDTOs;
	}
	
	@GetMapping(path="/all")
	public String allDepartmentsDTO(Model model) {
		model.addAttribute("departments", this.getAllDepDTOs());
		
		return "departments";
		
	}
	
	
	@GetMapping(path="/edit/{id}")
	public String edit(Model model,@PathVariable Long id) {
		Optional<Department> opt = departmentRepository.findById(id);
		Department department = opt.get();	
		model.addAttribute("departmentForm", department);
		model.addAttribute("locations", locationRepository.findAll());
		model.addAttribute("employees",employeeRepository.findAll());
		return "departmentEdit";
	}
	
	@PostMapping(path="/update")
	public String update(Model model, @ModelAttribute Department dep) {
		
		Optional<Department> opt = departmentRepository.findById(dep.getDepartmentId());
		Department department = opt.get();
		
		department.setDepartmentName(dep.getDepartmentName());
		department.setLocation(dep.getLocation());
		department.setManagerId(dep.getManagerId());
		
		
		departmentRepository.save(department);
		
		return "redirect:all";
	}
}
