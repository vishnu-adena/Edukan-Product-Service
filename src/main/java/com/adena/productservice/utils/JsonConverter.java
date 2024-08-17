package com.adena.productservice.utils;

import jakarta.persistence.Converter;

import com.adena.productservice.dto.ImageDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<List<ImageDTO>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ImageDTO> imageList) {
        try {
            return objectMapper.writeValueAsString(imageList);
        } catch (IOException e) {
            throw new RuntimeException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<ImageDTO> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<ImageDTO>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to list", e);
        }
    }
}
