package com.example.nour.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nour.model.JobHistory;
import com.example.nour.model.JobHistoryPK;
import com.example.nour.repository.DepartmentRepository;
import com.example.nour.repository.EmployeeRepository;
import com.example.nour.repository.JobHistoryRepository;
import com.example.nour.repository.JobRepository;

@Controller
@RequestMapping(path = "/jobHistory")
public class JobHistoryController {

	@Autowired
	private JobHistoryRepository jobHistoryRepository;
	
	@Autowired
	private JobRepository JobRepository;
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private DepartmentRepository depRepo;

	@GetMapping(path = "/all")
	public String allJobHistories(Model model) {
		model.addAttribute("jobHistory", jobHistoryRepository.findAll());
		return "jobHistory";
	}

	@GetMapping(path = "/edit/{idEmployeeId}/{idStartDate}")
	public String edit(Model model, @PathVariable Long idEmployeeId, @PathVariable String idStartDate) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = dateFormat.parse(idStartDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

		
		//Creation of the primary key
		JobHistoryPK jobPK = new JobHistoryPK();
		jobPK.setEmployeeId(idEmployeeId);
		jobPK.setStartDate(date);
		
				
		//Optional<JobHistory> opt = jobHistoryRepository.findOneByJobHistPK(jobPK);
		

//		JobHistory jobHistory = jobHistoryRepository.findById(jobPK).get();
		
		Optional<JobHistory> opt = jobHistoryRepository.findByIdEmployeeIdAndIdStartDate(idEmployeeId, sqlStartDate);

		JobHistory jobHistory = opt.get();
		
		model.addAttribute("jobHistoryForm", jobHistory);
		model.addAttribute("jobs", JobRepository.findAll());
		//model.addAttribute("emps", empRepo.findAll());
		model.addAttribute("departments", depRepo.findAll());

		return "jobHistoryEdit";

	}

}
