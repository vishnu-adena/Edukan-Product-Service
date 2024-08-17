package com.adena.productservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDTO {
    private String title;
    private String description;
    private String category;
    private double price;
    public List<ImageDTO> image;

}
