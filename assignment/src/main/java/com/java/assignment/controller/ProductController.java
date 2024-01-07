package com.java.assignment.controller;

import com.java.assignment.controller.message.ProductRequestMessage;
import com.java.assignment.controller.message.ProductResponse;
import com.java.assignment.controller.message.mapper.ProductMapper;
import com.java.assignment.model.Product;
import com.java.assignment.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing products.
 */
@RestController
@RequestMapping("product")
@Validated
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductMapper productMapper;

	@GetMapping("/products")
	public List<ProductResponse> allProducts() {
		return productMapper.toResponseList(productService.getAllProducts());
	}

	@GetMapping("/products/{productId}")
	public ProductResponse getProductById(@PathVariable Integer productId) {
		return productMapper.getProductResponse(productService.getProductById(productId));
	}

	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public void addProduct(@Valid @RequestBody ProductRequestMessage requestMessage) {
		productService.addProduct(productMapper.toProduct(requestMessage));
	}

	@PutMapping("/products/{productId}")
	public void updateProduct(@PathVariable Integer productId, @RequestBody ProductRequestMessage requestMessage) {
		productService.updateProduct(productId, requestMessage);
	}

	@DeleteMapping("/products/{productId}")
	public void deleteProductById(@PathVariable Integer productId) {
		productService.deleteProductById(productId);
	}

	@GetMapping("/productsByPage")
	public Page<Product> getProductsByPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productService.getProductsByPage(pageable);
	}

}
