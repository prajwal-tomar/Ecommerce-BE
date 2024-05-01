package com.ecommercestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercestore.models.Product;
import com.ecommercestore.request.CreateProductRequest;
import com.ecommercestore.response.ApiResponse;
import com.ecommercestore.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) throws Exception {
        Product product = productService.createProduct(req);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PostMapping("/create-bulk")
    public ResponseEntity<ApiResponse> createProductInBulk(@RequestBody CreateProductRequest[] req) throws Exception {
        for (CreateProductRequest product : req) {
            productService.createProduct(product);
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Products added successfully.");
        apiResponse.setStatus(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, Product product) throws Exception {
        return productService.updateProduct(id, product);
    }

    @GetMapping("/")
    ResponseEntity<Page<Product>> getAllProducts(@RequestParam(required = false) String category,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws Exception {
        // Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = productService.getAllProducts(category, colors, sizes, minPrice, maxPrice, minDiscount,
                sort, stock,
                pageNumber, pageSize);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
