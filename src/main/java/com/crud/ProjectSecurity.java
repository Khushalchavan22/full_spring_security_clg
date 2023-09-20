package com.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.crud.services.UserLoginService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class ProjectSecurity {

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		
		http.csrf(csrf_token -> csrf_token.disable());
		 
		http.authorizeHttpRequests(http_request -> http_request
				                                              .dispatcherTypeMatchers(DispatcherType.FORWARD,DispatcherType.ERROR).permitAll()
				                                              .requestMatchers(HttpMethod.POST,"/users/register").permitAll()
				                                              .requestMatchers(HttpMethod.GET.POST,"/products").hasAnyAuthority("HR","MANAGER")
				                                              .requestMatchers(HttpMethod.PUT.DELETE.POST.GET,"/products").hasAuthority("MANAGER")
				                                               .anyRequest()
				                                              .authenticated()).httpBasic(Customizer.withDefaults())
		                                                      .authenticationProvider(daoAuthenticationProvider());
		
	  return http.build();	
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
//		UserDetails user1 =User.builder().username("khushal").password(passwordEncoder().encode("khu@123")).roles("MANAGER").build();
//		UserDetails user2 =User.builder().username("vicky").password(passwordEncoder().encode("vic@123")).roles("HR").build();
//		UserDetails user3 =User.builder().username("mana").password(passwordEncoder().encode("mama@123")).roles("GUARD").build();
//		    
//		 return new InMemoryUserDetailsManager(user1,user2,user3);
//		
//	}
	
	@Autowired
	UserLoginService userLoginService;
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userLoginService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	

	
	
	
	
}
