package com.java.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.assignment.dao.ProductDao;
import com.java.assignment.model.Product;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {

	@Autowired
	ProductDao productDao;

	/**
	 * Get all products.
	 *
	 * @return ResponseEntity containing a list of products or an empty list if an
	 *         error occurs
	 */
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			return new ResponseEntity<>(productDao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Get a product by its ID.
	 *
	 * @param id ID of the product to retrieve
	 * @return ResponseEntity containing the requested product or an error message
	 */
	public ResponseEntity<?> getProductById(Integer id) {
		try {
			// Attempt to find a product in the database by its ID
			Optional<Product> product = productDao.findById(id);

			if (product.isPresent()) {
				return new ResponseEntity<>(product, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error retrieving product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Add a new product.
	 *
	 * @param product The product to be added
	 * @return ResponseEntity indicating success or failure of the operation
	 */
	public ResponseEntity<String> addProduct(Product product) {
		try {
			productDao.save(product);
			return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error adding product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Update an existing product.
	 *
	 * @param productId      ID of the product to be updated
	 * @param updatedProduct Updated product details
	 * @return ResponseEntity indicating success or failure of the operation
	 */
	public ResponseEntity<String> updateProduct(Integer productId, Product updatedProduct) {
		try {
			Optional<Product> existingProductOptional = productDao.findById(productId);

			if (existingProductOptional.isPresent()) {
				Product existingProduct = existingProductOptional.get();

				// Update properties of the existing product with the new values
				existingProduct.setName(updatedProduct.getName());
				existingProduct.setPrice(updatedProduct.getPrice());
				existingProduct.setDescription(updatedProduct.getDescription());

				// Save the updated product back to the database
				productDao.save(existingProduct);

				return new ResponseEntity<>("Product updated successfully.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error updating product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Delete a product by its ID.
	 *
	 * @param productId ID of the product to be deleted
	 * @return ResponseEntity indicating success or failure of the operation
	 */
	public ResponseEntity<String> deleteProductById(Integer productId) {
		try {
			// Attempt to find a product in the database by its ID to delete it.
			Optional<Product> existingProductOptional = productDao.findById(productId);
			// If product fount then delete it and return OK status with success message
			// else return not found.
			if (existingProductOptional.isPresent()) {
				productDao.deleteById(productId);
				return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error deleting product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Get all products with pagination support.
	 *
	 * @param pageable Pageable object for pagination
	 * @return Page of products
	 */
	public Page<Product> getAllProducts(Pageable pageable) {
		return productDao.findAll(pageable);
	}
}
