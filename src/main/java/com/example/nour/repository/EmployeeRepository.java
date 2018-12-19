package com.example.nour.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.nour.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
	List<Employee> findAllByFirstName(String firstName);
}
