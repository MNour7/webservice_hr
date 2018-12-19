package com.example.nour.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.nour.model.Job;

public interface JobRepository extends CrudRepository<Job, String>{
	List<Job> findByMinSalaryGreaterThanOrderByMinSalaryDesc(BigDecimal minSalary);
}
