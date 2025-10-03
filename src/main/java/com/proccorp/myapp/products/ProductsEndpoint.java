package com.proccorp.myapp.products;

import com.proccorp.myapp.products.model.Product;
import com.proccorp.myapp.products.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping({"/api/products", "/products"})
@Slf4j
public class ProductsEndpoint {

    private final ProductService productService;

    public ProductsEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        List<Product> allProducts = productService.getAllProducts();
        log.info("All products: {}", allProducts);
        return allProducts;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Product> createProduct(@PathVariable String productId, @RequestBody Product product) {
        if (productService.exists(productId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        product.setId(productId);
        Product saved = productService.save(product);
        URI location = URI.create(String.format("/products/%s", saved.getId()));
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> putProduct(@PathVariable String productId, @RequestBody Product product) {
        boolean existed = productService.exists(productId);
        Product saved = productService.putUpdate(productId, product);
        if (existed) {
            return ResponseEntity.ok(saved);
        } else {
            URI location = URI.create(String.format("/products/%s", saved.getId()));
            return ResponseEntity.created(location).body(saved);
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Product> patchProduct(@PathVariable String productId, @RequestBody Map<String, Object> fields) {
        Optional<Product> updated = productService.patchUpdate(productId, fields);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
