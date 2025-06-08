package com.kayky.controller;

import com.kayky.dto.request.CategoryPostRequest;
import com.kayky.dto.request.CategoryPutRequest;
import com.kayky.dto.response.CategoryGetResponse;
import com.kayky.dto.response.CategoryPostResponse;
import com.kayky.dto.response.CategoryPutResponse;
import com.kayky.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "v1/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryGetResponse>> findAll() {
        log.debug("Request received to list all products");

        var allCategories = service.findAll();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find category by id {}", id);

        var category = service.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryPostResponse> save(@RequestBody CategoryPostRequest postRequest) {
        log.debug("Request to save a new category");

        var category = service.save(postRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).body(category);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CategoryPutResponse> update(@PathVariable Long id, @RequestBody CategoryPutRequest putRequest) {
        log.debug("Request to update a category by id {}", id);

        var category = service.update(id, putRequest);
        ;

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Request to delete a category by id {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
