package com.example.nour.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.nour.model.Department;

@RepositoryRestResource(collectionResourceRel = "departement", path = "departement")
public interface DepartmentRepository extends CrudRepository<Department, Long> {

}
