package com.example.nour;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.springframework.test.context.junit4.SpringRunner;//I got the error in this line

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.nour.model.Job;
import com.example.nour.repository.JobRepository;

//the test run before this error unknown..of import repo & springRunner class.. any clue
@RunWith(SprigRunner.class)

@SpringBootTest
public class NourDemoApplicationTests {

	
	@Autowired
	JobRepository jobRepository;
	Job job;
	
	
	
	
	@Test
	public void contextLoads() {
		
		Optional<Job> op = jobRepository.findById("AC_ACCOUNT");
		Job job = op.get();
		job.setJobTitle("kahinaUpdate");
		jobRepository.save(job);
		
		assertThat("kahinaUpdate").isEqualTo(jobRepository.findById("AC_ACCOUNT"));
		//assertThat("kahinaUpdate").isEqualTo(jobRepository.findById("AC_ACCOUNT").getJobTitle());
	}
	
	

}

