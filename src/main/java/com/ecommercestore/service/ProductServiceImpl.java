package com.ecommercestore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommercestore.models.Category;
import com.ecommercestore.models.Product;
import com.ecommercestore.models.Size;
import com.ecommercestore.repository.CategoryRepository;
import com.ecommercestore.repository.ProductRepository;
import com.ecommercestore.request.CreateProductRequest;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req) throws Exception {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        // if this category does not exist, then set the category sent by the user in
        // the req object.
        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),
                secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);
            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        int totalQuantity = 0;
        for (Size size : req.getSizes()) {
            totalQuantity += size.getQuantity();
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercentage(req.getDiscountPercentage());
        product.setQuantity(totalQuantity);
        product.setBrand(req.getBrand());
        product.setColor(req.getColor());
        product.setSizes(req.getSizes());
        product.setImageUrl(req.getImageUrl());
        product.setNumRatings(0);
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws Exception {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found with ID: " + productId));

        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscountedPrice(product.getDiscountedPrice());
        existingProduct.setDiscountPercentage(product.getDiscountPercentage());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setColor(product.getColor());
        existingProduct.setSizes(product.getSizes());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setCategory(product.getCategory());

        // Save the updated product back to the database
        return productRepository.save(existingProduct);
    }

    @Override
    public Page<Product> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize)
            throws Exception {
        List<Product> products = productRepository.filteredProducts(category, minPrice, maxPrice, minDiscount, sort);

        // Filter products by colors
        if (colors != null && !colors.isEmpty()) {
            products = products.stream()
                    .filter(product -> colors.contains(product.getColor()))
                    .collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        // Filter products by sizes
        if (sizes != null && !sizes.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getSizes().stream()
                            .anyMatch(size -> sizes.contains(size.toString())))
                    .collect(Collectors.toList());
        }

        // Convert the filtered list to a Page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        return productPage;
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        Optional<Product> existingProduct = productRepository.findById(id);
        // If product with the given id is not found, throw exception
        if (!existingProduct.isPresent()) {
            throw new Exception("Product not found with ID: " + id);
        }
        // else return it.
        return existingProduct.get();
    }

    @Override
    public String deleteProduct(Long id) throws Exception {
        Product product = getProductById(id);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted successfully!";
    }

}
