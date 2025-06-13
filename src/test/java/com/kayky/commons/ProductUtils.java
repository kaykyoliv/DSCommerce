package com.kayky.commons;

import com.kayky.domain.Product;
import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import com.kayky.dto.response.ProductSummaryResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductUtils {

    private static final LocalDateTime fixedDateTime = LocalDateTime.parse(
            "2024-08-06T10:36:59.441524",
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    );

    public static List<Product> newProductList() {
        var iphone = Product.builder()
                .id(1L)
                .name("Iphone 8 plus")
                .description("Iphone")
                .quantity(15)
                .price(3350.0)
                .categories(Set.of(CategoryUtils.savedCategory()))
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();

        var carregador = Product.builder()
                .id(2L)
                .name("Carregador")
                .description("Carregador original iphone")
                .quantity(15)
                .price(150.0)
                .categories(Set.of(CategoryUtils.savedCategory()))
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();

        var capinha = Product.builder()
                .id(3L)
                .name("Capinha Iphone")
                .description("Capinha Iphone 8 verde")
                .quantity(15)
                .price(50.0)
                .categories(Set.of(CategoryUtils.savedCategory()))
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();

        return new ArrayList<>(List.of(iphone, carregador, capinha));
    }

    public static Product productToSave() {
        return buildProduct(null, "Teclado mecanico", "Teclado mecânico com switches azuis", 15, 350.0);
    }

    public static Product savedProduct() {
        return buildProduct(1L, "Teclado mecanico", "Teclado mecânico com switches azuis", 15, 350.0);
    }


    public static Product productToUpdate() {
        return buildProduct(1L, "Batedeira", "Batedeira para massas pesadas", 15, 350.0);
    }


    public static ProductGetResponse productGetResponse() {
        return ProductGetResponse.builder()
                .id(1L)
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .categories(Set.of(CategoryUtils.categoryGetResponse()))
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();
    }

    public static ProductPostRequest productPostRequest() {
        return ProductPostRequest.builder()
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .categoryIds(Set.of(1L))
                .price(350.0)
                .build();
    }

    public static ProductPostResponse productPostResponse() {
        return ProductPostResponse.builder()
                .id(1L)
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .categoryIds(Set.of(1L))
                .createdAt(fixedDateTime)
                .build();
    }

    public static ProductPutRequest productPutRequest() {
        return ProductPutRequest.builder()
                .name("Batedeira")
                .description("Batedeira para massas pesadas")
                .quantity(15)
                .price(350.0)
                .categoryIds(new HashSet<>(Set.of(1L)))
                .build();
    }

    public static ProductPutResponse productPutResponse() {
        return ProductPutResponse.builder()
                .id(1L)
                .name("Batedeira")
                .description("Batedeira para massas pesadas")
                .quantity(15)
                .price(350.0)
                .categoryIds(Set.of(1L))
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();
    }

    public static ProductSummaryResponse productSummaryResponse(){
        return ProductSummaryResponse.builder()
                .id(1L)
                .name("Teclado mecanico")
                .description("Teclado mecânico com switches azuis")
                .quantity(15)
                .price(350.0)
                .categoryIds(Set.of(1L))
                .build();
    }

    private static Product buildProduct(Long id, String name, String description, int quantity, double price) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .categories(new HashSet<>(Set.of(CategoryUtils.savedCategory())))
                .createdAt(fixedDateTime)
                .updatedAt(fixedDateTime)
                .build();
    }

}
