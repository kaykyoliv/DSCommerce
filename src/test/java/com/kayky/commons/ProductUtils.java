package com.kayky.commons;

import com.kayky.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductUtils {

    public Product productToSave(){
       return Product.builder()
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .build();
    }

    public Product productToUpdate(){
        return Product.builder()
                .id(1L)
                .name("Batedeira")
                .description("Batedeira para massas pesadas")
                .quantity(15)
                .price(350.0)
                .build();
    }
}
