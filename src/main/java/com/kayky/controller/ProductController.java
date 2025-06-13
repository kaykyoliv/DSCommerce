package com.kayky.controller;

import com.kayky.dto.request.ProductPostRequest;
import com.kayky.dto.request.ProductPutRequest;
import com.kayky.dto.response.ProductGetResponse;
import com.kayky.dto.response.ProductPostResponse;
import com.kayky.dto.response.ProductPutResponse;
import com.kayky.dto.response.ProductSummaryResponse;
import com.kayky.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "v1/products")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductSummaryResponse>> findAllPaged(@PageableDefault Pageable pageable) {
        log.debug("Request received to list all products");

        var allProducts = service.findAll(pageable);

        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find product by id {}", id);

        var product = service.findByIdOrThrowNotFound(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductPostResponse> save(@RequestBody ProductPostRequest request) {
        log.debug("request to create new product");

        var savedProduct = service.save(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(uri).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductPutResponse> update(@PathVariable Long id, @RequestBody ProductPutRequest request) {
        log.debug("request to update a product by id {}", id);

        var updatedProduct = service.update(id, request);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.debug("request to delete a product by id {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
