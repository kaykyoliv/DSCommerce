package com.kayky.controller;

import com.kayky.dto.response.ProductGetResponse;
import com.kayky.mapper.ProductMapper;
import com.kayky.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "v1/products")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductGetResponse>> findAll(){
        var products = service.findAll();
        var response = mapper.toProductGetResponseList(products);

        return ResponseEntity.ok(response);
    }
}
