package com.example.nour.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.nour.model.JobHistory;
import com.example.nour.model.JobHistoryPK;;

@RepositoryRestResource(collectionResourceRel = "jobHistory", path = "jobHistory")
public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryPK> {
	
	Optional<JobHistory> findById(JobHistoryPK jobHistoryPK);
//	List<JobHistory> findByIdEmployeeIdAndIdStartDate(long empId, Date start);

	Optional<JobHistory> findByIdEmployeeIdAndIdStartDate(Long idEmployeeId, java.sql.Date idStartDate);

}
