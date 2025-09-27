package com.proccorp.myapp.checkout;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutServiceTest {

    private CheckoutService checkoutService;

    // 4 produkty - ich ceny
    // 1 wiecej ale nie wystarczajaco do obnizki
    // 1 wiecej ale wystarczajaco do obnizki
    // duzo wicej.
    // mix produktow
    // pusty koszyk - 0


    @Test
    void test(){
        Product a = new Product("A");
        checkoutService = new CheckoutService();

        CheckoutSummary checkoutSummary = checkoutService.checkout(List.of(
                        Itinerary.builder()
                                .product(a)
                                .quantity(2)
                                .build()
                )
        );

        Assertions.assertThat(checkoutSummary).isNotNull();
        Assertions.assertThat(checkoutSummary.getPrice()).isEqualTo(new BigDecimal("80"));
    }

}


/*


A
40
3
30

B
10
2
7.5
C
30
4
20
D
25
2
23.5



 */