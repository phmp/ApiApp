package com.proccorp.myapp.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class QuantityDiscountPricingService implements PricingService {
    Map<String, BigDecimal> regPrice = Map.of("A", BigDecimal.valueOf(40));
    Map<String, Integer> reqQuantity =  Map.of("A", 3);
    Map<String, BigDecimal> specialPrice =  Map.of("A", BigDecimal.valueOf(30));

    @Override
    public BigDecimal getPrice(Itinerary ite) {
        var key = ite.getProduct().getName();
        if (ite.getQuantity() > reqQuantity.get(key)) {
            return specialPrice.get(key).multiply(BigDecimal.valueOf( ite.getQuantity()));
        } else {
            return regPrice.get(key).multiply(BigDecimal.valueOf( ite.getQuantity()));
        }
    }
}
