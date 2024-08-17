package com.adena.productservice.models;

import com.adena.productservice.dto.ImageDTO;
import com.adena.productservice.utils.JsonConverter;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product", schema = "productservice")// Define the JSON type
public class Product extends BaseModel {

    private String name;
    private String description;
    private Double price;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "text") // Use "text" for generic JSON storage
    private List<ImageDTO> image;
}
