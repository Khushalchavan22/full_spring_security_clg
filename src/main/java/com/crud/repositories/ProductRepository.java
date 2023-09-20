package com.crud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.crud.models.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
