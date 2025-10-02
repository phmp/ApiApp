package com.proccorp.myapp.products;

import com.proccorp.myapp.products.model.Product;
import com.proccorp.myapp.products.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
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
}
