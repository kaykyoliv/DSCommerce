package com.kayky.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryGetResponse {

    private Long id;
    private String name;
}
