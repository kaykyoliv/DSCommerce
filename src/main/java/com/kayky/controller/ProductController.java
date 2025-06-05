package com.kayky.controller;

import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.mapper.ProductMapper;
import com.kayky.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "v1/products")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductGetResponse>> findAll() {
        log.debug("Request received to list all products");
        var products = service.findAll();
        var response = mapper.toProductGetResponseList(products);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find product by id");
        var product = service.findById(id);
        var response = mapper.toProductGetResponse(product);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductPostResponse> save(@RequestBody ProductPostRequest request){
        log.debug("request to create new product");

        var productEntity = mapper.toProduct(request);
        var savedProduct = service.save(productEntity);
        var responseDto = mapper.toProductPostResponse(savedProduct);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }
}
