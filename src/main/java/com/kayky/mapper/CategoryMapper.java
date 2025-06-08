package com.kayky.mapper;

import com.kayky.domain.Category;
import com.kayky.dto.request.CategoryPostRequest;
import com.kayky.dto.request.CategoryPutRequest;
import com.kayky.dto.response.CategoryGetResponse;
import com.kayky.dto.response.CategoryPostResponse;
import com.kayky.dto.response.CategoryPutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    List<CategoryGetResponse> toCategoryGetResponseList(List<Category> categoryList);

    CategoryGetResponse toCategoryGetResponse(Category category);

    Category toCategory(CategoryPostRequest request);

    CategoryPostResponse toCategoryPostResponse(Category category);

    void updateCategoryFromRequest(CategoryPutRequest request, @MappingTarget Category category);

    CategoryPutResponse toCategoryPutResponse(Category category);
}
