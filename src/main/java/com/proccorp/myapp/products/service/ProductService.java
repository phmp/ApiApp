package com.proccorp.myapp.products.service;

import com.proccorp.myapp.products.model.Currency;
import com.proccorp.myapp.products.model.Price;
import com.proccorp.myapp.products.model.Product;
import com.proccorp.myapp.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public boolean exists(String id) {
        return productRepository.existsById(id);
    }

    public Optional<Product> getById(String id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product putUpdate(String id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    public Optional<Product> patchUpdate(String id, Map<String, Object> fields) {
        Optional<Product> existingOpt = productRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }
        Product existing = existingOpt.get();

        if (fields.containsKey("name")) {
            Object v = fields.get("name");
            existing.setName(v == null ? null : v.toString());
        }
        if (fields.containsKey("description")) {
            Object v = fields.get("description");
            existing.setDescription(v == null ? null : v.toString());
        }
        if (fields.containsKey("price")) {
            Object priceObj = fields.get("price");
            Price price = existing.getPrice();
            if (price == null) price = new Price();
            if (priceObj instanceof Map<?, ?> priceMap) {
                if (priceMap.containsKey("amount")) {
                    Object amountObj = priceMap.get("amount");
                    if (amountObj == null) {
                        price.setAmount(null);
                    } else if (amountObj instanceof Number n) {
                        price.setAmount(new BigDecimal(n.toString()));
                    } else {
                        price.setAmount(new BigDecimal(amountObj.toString()));
                    }
                }
                if (priceMap.containsKey("currency")) {
                    Object currObj = priceMap.get("currency");
                    if (currObj == null) {
                        price.setCurrency(null);
                    } else if (currObj instanceof Currency c) {
                        price.setCurrency(c);
                    } else {
                        price.setCurrency(Currency.valueOf(currObj.toString()));
                    }
                }
            } else if (priceObj == null) {
                price = null;
            }
            existing.setPrice(price);
        }

        Product saved = productRepository.save(existing);
        return Optional.of(saved);
    }
}
