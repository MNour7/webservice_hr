package com.example.nour.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nour.model.Job;
import com.example.nour.repository.JobRepository;

@Controller
@RequestMapping(path="/job")
public class JobController {
	
	@Autowired
	private JobRepository jobRepository;
	
	@GetMapping(path="/all")
	public String allJob(Model model) {
		model.addAttribute("jobs", jobRepository.findAll());
		return "jobs";
	}
	
	@GetMapping(path="/edit/{id}")
	public String edit(Model model,@PathVariable String id) {
		Optional<Job> opt = jobRepository.findById(id);
		
		Job job = opt.get();
		model.addAttribute("jobForm", job);
		
		return "jobEdit";
	}
	
	@PostMapping(path="/update")
	public String update(Model model, @ModelAttribute Job jo) {
		
		Optional<Job> opt =jobRepository.findById(jo.getJobId());
		
		Job job = opt.get();
		job.setJobTitle(jo.getJobTitle());
		job.setMaxSalary(jo.getMaxSalary());
		job.setMinSalary(jo.getMinSalary());
		
		jobRepository.save(job);
		
		return "redirect:all";
	}
	
	@GetMapping(path="/update/{id}/{name}")
	public  @ResponseBody Job update(@PathVariable String id, @PathVariable String name){
		 
		Optional<Job> op = jobRepository.findById(id);
		Job job = op.get();
		job.setJobTitle(name);
		
		jobRepository.save(job);
		
		return job;
	}
	
	@GetMapping(path="/minSalary/{min}")
	public @ResponseBody List<Job> minSalary(@PathVariable BigDecimal min){
		return jobRepository.findByMinSalaryGreaterThanOrderByMinSalaryDesc(min);
	}
	
	@GetMapping(path="/alljson")
	public @ResponseBody List<Job> allJob(){
		return (List<Job>) jobRepository.findAll();
	}	
	

} 
