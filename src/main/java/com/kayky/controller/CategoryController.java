package com.kayky.controller;

import com.kayky.dto.response.CategoryGetResponse;
import com.kayky.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "v1/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryGetResponse>> findAll(){
        log.debug("Request received to list all products");

        var allCategories = service.findAll();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryGetResponse> findById(@PathVariable Long id){
        log.debug("Request to find category by id {}", id);

        var category = service.findById(id);
        return ResponseEntity.ok(category);
    }
}
