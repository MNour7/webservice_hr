package com.example.nour.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.nour.model.JobHistory;;

@RepositoryRestResource(collectionResourceRel = "jobHistory", path = "jobHistory")
public interface JobHistoryRepository extends CrudRepository<JobHistory, String> {

	Optional<JobHistory> findByIdEmployeeIdAndIdStartDate(Long idEmployeeId, java.sql.Date idStartDate);



}
