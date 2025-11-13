package com.rentease.service;

import com.rentease.model.Product;
import com.rentease.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Add a new product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update existing product
    public Product updateProduct(String id, Product updatedProduct) {
        Optional<Product> existing = productRepository.findById(id);
        if(existing.isPresent()) {
            Product prod = existing.get();
            prod.setName(updatedProduct.getName());
            prod.setDescription(updatedProduct.getDescription());
            prod.setPrice(updatedProduct.getPrice());
            prod.setLatitude(updatedProduct.getLatitude());
            prod.setLongitude(updatedProduct.getLongitude());
            prod.setAvailable(updatedProduct.isAvailable());
            return productRepository.save(prod);
        }
        return null;
    }

    // Delete product
    public boolean deleteProduct(String id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // Search products by name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Search products by location (lat/lng range)
    public List<Product> searchProductsByLocation(double latMin, double latMax, double lonMin, double lonMax) {
        return productRepository.findByLatitudeBetweenAndLongitudeBetween(latMin, latMax, lonMin, lonMax);
    }
}
