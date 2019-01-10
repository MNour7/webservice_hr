package com.example.nour.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.example.nour.model.Department;
import com.example.nour.model.Employee;
import com.example.nour.model.EmployeeDTO;
import com.example.nour.model.Job;
import com.example.nour.model.UserService;
import com.example.nour.repository.DepartmentRepository;
import com.example.nour.repository.EmployeeRepository;
import com.example.nour.repository.JobRepository;

@Controller
@RequestMapping(path="/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private UserService userService;
	
	//private Logger
	
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping(path="/all")
	public String allEmployee(Model model) {
		model.addAttribute("employees", employeeRepository.findAll());
		return "employees";
	}
	
	@GetMapping(path="/edit/{id}")
	public String allEmployee(Model model, @PathVariable long id) {
		model.addAttribute("jobs", jobRepository.findAll());
		model.addAttribute("departments", departmentRepository.findAll());
		model.addAttribute("managers", employeeRepository.findAll());
		model.addAttribute("employeeForm", employeeRepository.findById(id).get());
		return "employeeEdit";
	}
	
	@PostMapping(path="/update")
	public String update(Model model, @ModelAttribute Employee empForm) {
		Employee emp = employeeRepository.findById(empForm.getEmployeeId()).get();
		Job job = jobRepository.findById(empForm.getJob().getJobId()).get();
		Department dep = departmentRepository.findById(empForm.getDepartment().getDepartmentId()).get();
		
		int depId = (int) dep.getDepartmentId();
		switch (depId) {
			case 10:
				emp.setRole("ROLE_CEO");
				break;
				
			case 100:
				emp.setRole("ROLE_FIN_AC");
				break;
				
			case 110:
				emp.setRole("ROLE_FIN_AC");
				break;
				
			case 80:
				emp.setRole("ROLE_SALES");
				break;
	
			default:
				break;
		}
		
		emp.setFirstName(empForm.getFirstName());
		emp.setLastName(empForm.getLastName());
		emp.setEmail(empForm.getEmail());
		emp.setPhoneNumber(empForm.getPhoneNumber());
		emp.setCommissionPct(empForm.getCommissionPct());
		emp.setSalary(empForm.getSalary());
		emp.setHireDate(empForm.getHireDate());
		emp.setManagerId(empForm.getManagerId());
		emp.setJob(job);
		emp.setDepartment(dep);
		
		if(empForm.getPassword() != "" && empForm.getPassword() != emp.getPassword()) {
			emp.setPassword(empForm.getPassword());
			userService.saveEmpAut(emp);
		}			
		else
			employeeRepository.save(emp);
		
		return "redirect:all";
	}
	
	
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
		List<Employee> emps = employeeRepository.findAllByOrderBySalaryAsc();
		ArrayList<BigDecimal> sals = new ArrayList<>();
		ArrayList<BigDecimal> deciles = new ArrayList<>();
		
		for (int i = 0; i < emps.size(); i++) {
			sals.add(emps.get(i).getSalary());
		}
		
		int n = sals.size();
		int indLeft = 0;
		int indRight = 0;
		
		ArrayList<Double> reparts = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			indRight = Math.round((i*n)/10);
			deciles.add(sals.get(indRight));
			indRight = sals.lastIndexOf(sals.get(indRight));
			//recup taille de la classe
			List<BigDecimal> tab = sals.subList(indLeft, indRight+1);
			double portion = tab.size();

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
