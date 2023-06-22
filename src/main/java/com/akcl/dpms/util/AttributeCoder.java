package com.akcl.dpms.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.core.GenericTypeResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class AttributeCoder<T> {
    private final Class<T> attributeType;
    private final ObjectMapper attributeMapper;

    String nameField;
    String valueField;
    String sidField;
    String ridField;

    String oidField;

    Map<String, Map<String, Object>> attributeStore = new HashMap<>();
    Map<String, Integer> attributeIndexMap = new HashMap<>();

    public AttributeCoder(String nameField, String valueField, String sidField, String ridField) {
        this.attributeType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AttributeCoder.class);
        this.attributeMapper = new ObjectMapper().addMixIn(Object.class, DynamicMapperMixin.class).setFilterProvider(new SimpleFilterProvider()
                        .addFilter("dynamic_mapper_filter", SimpleBeanPropertyFilter.filterOutAllExcept(nameField, valueField, sidField, ridField)));

        this.nameField = nameField;
        this.valueField = valueField;
        this.sidField = sidField;
        this.ridField = ridField;
    }

    public void insertAttribute(T attribute, Map<String, Object> dest) {
        Map<String, Object> attributeNode = attributeMapper.convertValue(attribute, new TypeReference<Map<String, Object>>() {});

        String attrName = String.valueOf(attributeNode.get(nameField));
        dest.put(attrName + "", attributeNode.get(valueField));
        dest.put(attrName + "__sid", attributeNode.get(sidField));
        dest.put(attrName + "__rid", attributeNode.get(ridField));
    }

    public void insertAttribute(String attrName, Object value, List<T> dest) {
        Map<String, Object> attrFields = attributeStore.computeIfAbsent(attrName, k -> new HashMap<>());

        if (attrName.endsWith("__rid")) {
            attrFields.put(ridField, Optional.ofNullable(value).map(v -> Long.valueOf("" + v)).orElse(null));
            attrName = attrName.replace("__rid", "");
        } else if (attrName.endsWith("__sid")) {
            attrFields.put(sidField, value);
            attrName = attrName.replace("__sid", "");
        } else {
            attrFields.put(nameField, attrName);
            attrFields.put(valueField, value);
            attrName = attrName.concat("");
        }

        int attributeIndex = attributeIndexMap.computeIfAbsent(attrName, k -> dest.size());
        T attribute = new ObjectMapper().convertValue(attrFields, attributeType);

        if (attributeIndex == dest.size()) {
            dest.add(attributeIndex, attribute);
        } else {
            dest.set(attributeIndex, attribute);
        }
    }
}
