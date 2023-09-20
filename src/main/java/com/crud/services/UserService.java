package com.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.crud.models.User;
import com.crud.repositories.UserRepository;
import com.crud.responcewrapper.UserRR;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	public ResponseEntity<?>register(User user)
	{
		 String userdata=user.getEmail();
		 boolean useremail_exist=userRepository.findByEmail(userdata).isPresent();
	   
		
		 if(useremail_exist)
		 {
			 throw new  ResponseStatusException(HttpStatus.FOUND,"UserEmail already Exist");
 
		 }
		 else if(!user.getPassword().equals(user.getConfpassword()))
		 {
			 throw new  ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"password and confpassword do not match plz chack it");
		 }
		 else
		 {
			 user.setPassword(encoder.encode(user.getPassword()));
		        User Register = userRepository.save(user);
		     UserRR tr=new UserRR();
		     tr.setMessage("Your Registration Succesfully Done");
          tr.setData(Register);
          return new ResponseEntity<>(tr,HttpStatus.CREATED);
			//throw new  ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"password and confpassword do not match plz chack it");
	 
		 }
		
	}
	
}
