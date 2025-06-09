package com.kayky.mapper;

import com.kayky.domain.Category;
import com.kayky.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProductMapperHelper {

    private final CategoryRepository categoryRepository;

    public Set<Category> mapCategoryIdsToCategories(Set<Long> ids){
        List<Category> found = categoryRepository.findAllById(ids);
        if(found.size() != ids.size()){
            throw new IllegalArgumentException("One or more category IDs not found.");
        }
        return new HashSet<>(found);
    }
}
