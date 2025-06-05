package com.kayky.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductPostRequest {

    private String name;
    private String description;
    private Integer quantity;
    private Double price;

}
