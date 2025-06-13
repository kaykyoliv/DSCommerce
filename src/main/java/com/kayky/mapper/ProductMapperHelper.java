package com.kayky.mapper;

import com.kayky.domain.Category;
import com.kayky.repository.CategoryRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductMapperHelper {

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductMapperHelper() {
    }

    public Set<Category> mapCategoryIdsToCategories(Set<Long> ids){
        List<Category> found = categoryRepository.findAllById(ids);
        if(found.size() != ids.size()){
            throw new IllegalArgumentException("One or more category IDs not found.");
        }
        return new HashSet<>(found);
    }
}
