package com.java.assignment.service;

import com.java.assignment.controller.message.ProductRequestMessage;
import com.java.assignment.dao.ProductRepository;
import com.java.assignment.exception.NotFoundException;
import com.java.assignment.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Integer id) {
		Optional<Product> product = productRepository.findById(id);

		if (product.isPresent()) {
			return product.get();
		} else {
			throw new NotFoundException("Product with id: " + id + " not found");
		}
	}

	public void addProduct(Product product) {
		productRepository.save(product);
	}

	@Transactional
	public void updateProduct(Integer productId, ProductRequestMessage updatedProductMessage) {

		Optional<Product> existingProductOptional = productRepository.findById(productId);

		if (existingProductOptional.isPresent()) {
			Product existingProduct = existingProductOptional.get();
			existingProduct.setName(updatedProductMessage.getName());
			existingProduct.setPrice(updatedProductMessage.getPrice());
			existingProduct.setDescription(updatedProductMessage.getDescription());
		} else {
			throw new NotFoundException("Product with id: " + productId + " not found");
		}
	}

	public void deleteProductById(Integer productId) {
		Optional<Product> existingProductOptional = productRepository.findById(productId);

		if (existingProductOptional.isPresent()) {
			productRepository.deleteById(productId);
		} else {
			throw new NotFoundException("Product with Id: " + productId + " not found.");
		}
	}

	public Page<Product> getProductsByPage(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
}
