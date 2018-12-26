package com.example.nour.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nour.repository.EmployeeRepository;

@Service
public class UserService implements UserDetailsService {
	
	public BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	private EmployeeRepository employeeRepository;
	
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
			return new org.springframework.security.core.userdetails.User(emp.getEmail(), emp.getPassword(),
					true,true,true,true, getAuthorities(emp.getRoles()));
		}	
		
		throw new UsernameNotFoundException(email);
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (UserRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.name()));
		}
		
        return authorities;
    }
	
	public void saveEmpAut(Employee emp) {
		emp.setPassword(bCryptPasswordEncoder.encode(emp.getPassword()));
		
		employeeRepository.save(emp);
	}

}
