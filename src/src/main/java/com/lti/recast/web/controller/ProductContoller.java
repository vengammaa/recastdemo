package com.lti.recast.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.recast.jpa.entity.Product;
import com.lti.recast.service.ConnectionService;
import com.lti.recast.service.ProductService;
import com.lti.recast.web.model.ProductModel;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/product")
public class ProductContoller {

	@Autowired(required = false) 
	ProductService productService;
	
	@PostMapping("/filter")
	public List<Product> getProductData(@RequestBody Product p) {
		
		System.out.println("product Model:"+p);
		List<Product>res = productService.getFilteredData(p);
		System.out.println(res);
		return res;
		
	}
	
	
}
