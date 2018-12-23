package com.example.nour.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nour.repository.EmployeeRepository;

@Service
public class UserService implements UserDetailsService {
	
	public BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	EmployeeRepository employeeRepository;
	
	@Autowired
	public UserService(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.employeeRepository = employeeRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Employee emp = employeeRepository.findByEmail(email);
		if(emp != null) {
			emp.addRoles(emp.getRole());
			return new org.springframework.security.core.userdetails.User(emp.getEmail(), emp.getPassword(), emp.getRoles());
		}	
		
		throw new UsernameNotFoundException(email);
	}
	
	public void saveEmpAut(Employee emp) {
		emp.setPassword(bCryptPasswordEncoder.encode(emp.getPassword()));
		
		employeeRepository.save(emp);
	}

}
