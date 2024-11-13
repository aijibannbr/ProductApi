package com.example.productapi.controller;
import com.example.productapi.model.Product;
import com.example.productapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product
                                                         product) {
        return new ResponseEntity<>(service.createProduct(product),
                HttpStatus.CREATED);
    }
    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long
                                                          id) {
        return service.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long
                                                         id, @RequestBody Product product) {
        Product updated = service.updateProduct(id, product);
        return updated != null ? ResponseEntity.ok(updated) :
                ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}