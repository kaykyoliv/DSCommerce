package com.kayky.commons;

import com.kayky.domain.Category;
import com.kayky.dto.response.CategoryGetResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryUtils {

    public static Category savedCategory(){
       return Category.builder()
                .id(1L)
                .name("Technology")
                .build();
    }

    public static CategoryGetResponse categoryGetResponse(){
        return CategoryGetResponse.builder()
                .id(1L)
                .name("Technology")
                .build();
    }


}
