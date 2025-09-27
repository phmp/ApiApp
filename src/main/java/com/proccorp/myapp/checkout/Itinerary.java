package com.proccorp.myapp.checkout;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Itinerary {
    Product product;
    int quantity;
}
