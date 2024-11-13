package com.example.productapi.service;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    private final ProductRepository repository;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }
    public Product createProduct(Product product) {
        return repository.save(product);
    }
    public Product updateProduct(Long id, Product product) {
        if (repository.existsById(id)) {
            product.setId(id);
            return repository.save(product);
        }
        return null;
    }
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
