package com.lti.recast.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.lti.recast.jpa.entity.Product;


public interface ProductRepository extends CrudRepository<Product,Integer>,JpaSpecificationExecutor<Product>
{

	
}
