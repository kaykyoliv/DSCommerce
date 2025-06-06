package com.kayky.commons;

import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductUtils {

    public List<Product> newProductList() {
        var dateTime = "2024-08-06T10:36:59.441524";
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        var localDateTime = LocalDateTime.parse(dateTime, formatter);

        var iphone = Product.builder()
                .id(1L)
                .name("Iphone 8 plus")
                .description("Iphone ")
                .quantity(15)
                .price(3350.0)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();

        var carregador = Product.builder()
                .id(2L)
                .name("Carregador")
                .description("Carregador original iphone")
                .quantity(15)
                .price(150.0)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();

        var capinha = Product.builder()
                .id(3L)
                .name("Capinha Iphone")
                .description("Capinha Iphone 8 verde")
                .quantity(15)
                .price(50.0)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();

        return new ArrayList<>(List.of(iphone, carregador, capinha));
    }

    public Product productToSave() {
        return Product.builder()
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .build();
    }

    public ProductPostRequest productPostRequest() {
        return ProductPostRequest.builder()
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .build();
    }


    public Product savedProduct() {
        return Product.builder()
                .id(1L)
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .build();
    }

    public Product productToUpdate() {
        return Product.builder()
                .id(1L)
                .name("Batedeira")
                .description("Batedeira para massas pesadas")
                .quantity(15)
                .price(350.0)
                .build();
    }
}
