package com.example.ProductService2.repository;
import com.example.ProductService2.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

}
