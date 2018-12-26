package com.example.nour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.example.nour.model.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/webjars/**").permitAll()
                .antMatchers("/**").hasRole("CEO")
                //.antMatchers("/home","/employee/**","/job/**","/region/all","/countries/all","/location/all","/department/all","/jobHistory/all").hasRole("FIN_AC")
//                .antMatchers().hasRole("FIN_AC")
//                .antMatchers("/region/edit/**","/countries/edit/**","/location/edit/**","/department/edit/**","/jobHistory/edit/**").hasRole("CEO")
//                .antMatchers("/employee/all","/employee/edit/**","/job/all","job/edit/**").hasRole("CEO")
                //.antMatchers("/region/all","/country/all","/location/all").hasRole("SALES")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
		 .userDetailsService(userDetailsService)
		  .passwordEncoder(bCryptPasswordEncoder);
//                .inMemoryAuthentication()
//                .withUser("SKING").password("sking").roles("CEO")
//                .and()
//                .withUser("robert").password("tata").roles("FINANCE");
	}
    
}
