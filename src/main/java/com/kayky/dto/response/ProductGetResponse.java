package com.kayky.dto.response;

import com.kayky.domain.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
public class ProductGetResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<CategoryGetResponse> categories;
}
