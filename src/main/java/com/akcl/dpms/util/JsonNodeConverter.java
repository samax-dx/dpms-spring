package com.akcl.dpms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.Optional;


public class JsonNodeConverter implements AttributeConverter<JsonNode, String>
{
    @Override
    public String convertToDatabaseColumn(JsonNode jsonNode)
    {
        if( jsonNode == null) {
            return null;
        }

        return jsonNode.toPrettyString();
    }

    @Override
    public JsonNode convertToEntityAttribute(String jsonNodeString) {
        if (Optional.ofNullable(jsonNodeString).map(v -> v.trim().length() > 0).orElse(false)) {
            return null;
        }

        try {
            return new ObjectMapper().readTree(jsonNodeString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
