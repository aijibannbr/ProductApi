package com.example.productapi.service;

import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class ProductServiceTest {
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductService service;
    private Product product;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1200.00);
        product.setDescription("A high-end laptop");
    }
    @Test
    void testGetAllProducts() {
        when(repository.findAll()).thenReturn(List.of(product));
        List<Product> products = service.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
        verify(repository, times(1)).findAll();
    }
    @Test
    void testGetProductById() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = service.getProductById(1L);
        assertTrue(foundProduct.isPresent());
        assertEquals("Laptop", foundProduct.get().getName());
        verify(repository, times(1)).findById(1L);
    }
    @Test
    void testCreateProduct() {
        when(repository.save(product)).thenReturn(product);
        Product createdProduct = service.createProduct(product);
        assertNotNull(createdProduct);
        assertEquals("Laptop", createdProduct.getName());
        verify(repository, times(1)).save(product);
    }
}
