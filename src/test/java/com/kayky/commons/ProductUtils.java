package com.kayky.commons;

import com.kayky.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductUtils {

    public Product createNewProduct(){
       return Product.builder()
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .build();
    }
}
