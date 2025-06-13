package com.kayky.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
public class ProductPostResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;

    private LocalDateTime createdAt;

    private Set<Long> categoryIds;
}
