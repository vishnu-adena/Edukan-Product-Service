package com.adena.productservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ResponseDTO {
    private long id;
    private Date created_At;
    private Date updated_At;
    private String name;
    private double price;
    private String description ;
    private String category;
    private List<ImageDTO> image;
}
