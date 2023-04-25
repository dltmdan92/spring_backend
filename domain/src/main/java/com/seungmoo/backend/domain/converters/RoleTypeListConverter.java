package com.seungmoo.backend.domain.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.seungmoo.backend.configuration.utils.ObjectMapperUtils;
import com.seungmoo.backend.domain.constants.RoleType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class RoleTypeListConverter implements AttributeConverter<List<RoleType>, String> {
    private final TypeReference<List<RoleType>> type = new TypeReference<>() {};

    @Override
    public String convertToDatabaseColumn(List<RoleType> attribute) {
        try {
            return ObjectMapperUtils.toString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RoleType> convertToEntityAttribute(String dbData) {
        try {
            return ObjectMapperUtils.readString(dbData, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
