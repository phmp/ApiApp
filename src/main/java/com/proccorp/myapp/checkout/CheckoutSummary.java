package com.proccorp.myapp.checkout;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CheckoutSummary {

    private final BigDecimal price;
    private final BigDecimal quantity;

}
