package com.kayky.commons;

import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ProductUtils {

    private final LocalDateTime fixedDateTime = LocalDateTime.parse(
            "2024-08-06T10:36:59.441524",
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    );

    public List<Product> newProductList() {
        return List.of(
                buildProduct(1L, "Iphone 8 plus", "Iphone", 15, 3350.0),
                buildProduct(2L, "Carregador", "Carregador original iphone", 15, 150.0),
                buildProduct(3L, "Capinha Iphone", "Capinha Iphone 8 verde", 15, 50.0)
        );
    }

    public Product productToSave() {
        return buildProduct(null, "Teclado mecanico", "Teclado mecânico com switches azuis", 15, 350.0);
    }

    public Product savedProduct() {
        return buildProduct(1L, "Teclado mecanico", "Teclado mecânico com switches azuis", 15, 350.0);
    }


    public Product productToUpdate() {
        return buildProduct(1L, "Batedeira", "Batedeira para massas pesadas", 15, 350.0);
    }

    public List<ProductGetResponse> productGetResponseList() {
        return newProductList().stream()
                .map(p -> ProductGetResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .build())
                .toList();
    }


    public ProductGetResponse productGetResponse() {
        return ProductGetResponse.builder()
                .id(1L)
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
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

    public ProductPostResponse productPostResponse() {
        return ProductPostResponse.builder()
                .id(1L)
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .createdAt(fixedDateTime)
                .build();
    }

    public ProductPutRequest productPutRequest() {
        return ProductPutRequest.builder()
                .name("Batedeira")
                .description("Batedeira para massas pesadas")
                .quantity(15)
                .price(350.0)
                .build();
    }

    public ProductPutResponse productPutResponse() {
        return ProductPutResponse.builder()
                .id(1L)
                .name("Batedeira")
                .description("Batedeira para massas pesadas")
                .quantity(15)
                .price(350.0)
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();
    }


    private Product buildProduct(Long id, String name, String description, int quantity, double price) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();
    }

}
