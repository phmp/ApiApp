package com.proccorp.myapp.checkout;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutService {

    PricingService pricingService = new QuantityDiscountPricingService();

    CheckoutSummary checkout(List<Itinerary> itineraries){
        var result = itineraries.stream()
                .map(ite -> pricingService.getPrice(ite))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        return CheckoutSummary.builder()
                .price(result)

                .build();
    }


}
