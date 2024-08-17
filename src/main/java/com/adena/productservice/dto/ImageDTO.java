package com.adena.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private String publicId;
    private String secureUrl;
    private String originalFileName;
    private String displayName;
}
