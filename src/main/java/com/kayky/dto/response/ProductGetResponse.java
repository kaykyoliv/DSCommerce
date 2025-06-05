package com.kayky.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
public class ProductGetResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
}
