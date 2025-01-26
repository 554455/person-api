package com.umaraliev.personapi.converter;

import com.google.gson.Gson;
import com.umaraliev.personapi.dto.IndividualDTO;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ConverterHistoryJSONB implements AttributeConverter<IndividualDTO, String> {

    private final static Gson GSON = new Gson();

    @Override
    public String convertToDatabaseColumn(IndividualDTO individualUpdateDTO) {
        if (individualUpdateDTO == null) {
            return null;
        }
        return GSON.toJson(individualUpdateDTO);
    }

    @Override
    public IndividualDTO convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return GSON.fromJson(s, IndividualDTO.class);
    }
}
