package com.proccorp.myapp.checkout;

import java.math.BigDecimal;

public interface PricingService {

    BigDecimal getPrice(Itinerary ite);
}
