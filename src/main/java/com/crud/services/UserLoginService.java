package com.crud.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.models.User;
import com.crud.repositories.UserRepository;

@Service
public class UserLoginService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	 
		 User founduser = userRepository.findByEmail(email).orElseThrow(
		    		() -> 
		    		{
		    	throw new UsernameNotFoundException(email);
		    		}
		    		
		    		);
		 
		       String userEmail=founduser.getEmail();
		       String userPassword=founduser.getPassword();
		       List<String>userRole=founduser.getRoles();
		       
		       Collection<GrantedAuthority>authorities=new ArrayList<>();
		       
		       for(String role:userRole)
		       {
		    	    authorities.add(new SimpleGrantedAuthority(role));
		       }
		       
		       User user=new User(userPassword, userEmail, authorities);
		 
		    
		
		return user;
	}

	
	
	
}
