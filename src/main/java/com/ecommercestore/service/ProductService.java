package com.ecommercestore.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommercestore.models.Product;
import com.ecommercestore.request.CreateProductRequest;

public interface ProductService {
    // Create a new product
    public Product createProduct(CreateProductRequest req) throws Exception;;

    // Update product
    public Product updateProduct(Long productId, Product product) throws Exception;

    // Filter products based on specific criteria
    public Page<Product> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize)
            throws Exception;

    // Get a product by ID
    public Product getProductById(Long id) throws Exception;

    // Delete a product by ID
    public String deleteProduct(Long id) throws Exception;
}
