package com.example.nour.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.nour.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	List<Employee> findAllByFirstName(String firstName);
	List<Employee> findAllByOrderBySalaryAsc();
}
