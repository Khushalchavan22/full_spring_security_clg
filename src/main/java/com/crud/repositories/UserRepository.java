package com.crud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.crud.models.User;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer> {


	 @RestResource(exported = false)
	 Optional<User> findByEmail(String email);
	 
	 @RestResource(exported = false)
	 <S extends User> S save(S entity);
	
	
	
}
