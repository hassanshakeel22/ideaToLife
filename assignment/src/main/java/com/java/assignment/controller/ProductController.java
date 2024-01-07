package com.java.assignment.controller;

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

import com.java.assignment.model.Product;
import com.java.assignment.service.ProductService;

import jakarta.validation.Valid;

/**
 * REST Controller for managing products.
 */
@RestController
@RequestMapping("product")
@Validated
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * Get all products.
     *
     * @return ResponseEntity containing a list of products
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> allProducts() {
        return productService.getAllProducts();
    }

    /**
     * Get a product by its ID.
     *
     * @param productId ID of the product to retrieve
     * @return ResponseEntity containing the requested product or an error message
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    /**
     * Add a new product.
     *
     * @param product The product to be added, validated using JSR-380 annotations
     * @return ResponseEntity indicating success or failure of the operation
     */
    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }

    /**
     * Update an existing product.
     *
     * @param productId      ID of the product to be updated
     * @param updatedProduct Updated product details
     * @return ResponseEntity indicating success or failure of the operation
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer productId, @RequestBody Product updatedProduct) {
        return productService.updateProduct(productId, updatedProduct);
    }

    /**
     * Delete a product by its ID.
     *
     * @param productId ID of the product to be deleted
     * @return ResponseEntity indicating success or failure of the operation
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable Integer productId) {
        return productService.deleteProductById(productId);
    }

    /**
     * Get all products with pagination support.
     *
     * @param page Page number (default is 0)
     * @param size Number of items per page (default is 10)
     * @return ResponseEntity containing a paginated list of products
     */
    @GetMapping("/productsByPage")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Exception handler for handling validation errors.
     *
     * @param ex MethodArgumentNotValidException thrown during validation
     * @return ResponseEntity containing a formatted validation error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = "Validation error(s): ";

        // Build a comma-separated list of validation errors
        errorMessage += bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2).orElse("Unknown error");

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
