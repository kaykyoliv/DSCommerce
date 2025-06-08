package com.kayky.mapper;

import com.kayky.domain.Category;
import com.kayky.dto.response.CategoryGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    List<CategoryGetResponse> toCategoryGetResponseList(List<Category> categoryList);

    CategoryGetResponse toCategoryGetResponse(Category category);
}
