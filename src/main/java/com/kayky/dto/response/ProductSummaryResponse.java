package com.kayky.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
public class ProductSummaryResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;

    private Set<Long> categoryIds;

}
