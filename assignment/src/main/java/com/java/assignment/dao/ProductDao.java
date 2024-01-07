package com.java.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.assignment.model.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
