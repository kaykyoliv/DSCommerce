package com.kayky.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Builder
public class ProductPutResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<Long> categoryIds;
}
