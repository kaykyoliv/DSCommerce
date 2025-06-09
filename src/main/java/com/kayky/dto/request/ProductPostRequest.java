package com.kayky.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class ProductPostRequest {

    private String name;
    private String description;
    private Integer quantity;
    private Double price;

    private Set<Long> categoryIds;

}
