package com.example.nour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                .antMatchers("/region/edit/**","/country/edit/**","/location/edit/**","/department/edit/**","/jobHistory/edit/**").hasRole("CEO")
                .antMatchers("/employee/**","/job/**","/region/all","/country/all","/location/all","/department/all","/jobHistory/all").hasAnyRole("CEO","FIN_AC")        
                .antMatchers("/region/all","/country/all","/location/all").hasAnyRole("SALES","CEO","FIN_AC")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied.html");
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
