package com.kayky.service;

import com.kayky.domain.Category;
import com.kayky.dto.response.CategoryGetResponse;
import com.kayky.mapper.CategoryMapper;
import com.kayky.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    @Transactional(readOnly = true)
    public List<CategoryGetResponse> findAll(){
        List<Category> categoryList = repository.findAll();
        return mapper.toCategoryGetResponseList(categoryList);
    }


    @Transactional(readOnly = true)
    public CategoryGetResponse findById(Long id){
        var category = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return mapper.toCategoryGetResponse(category);
    }

}
