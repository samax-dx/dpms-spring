package com.akcl.dpms.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class EntityUtil {
    public static class Attribute {
        public String key;
        public Object value;
        public Long rid;
        public String tid;
    }

    public static <T> Map<String, Object> getMapFromAttributes(List<T> attributes,
                                                               Function<T, String> getName,
                                                               Function<T, Object> getValue,
                                                               Function<T, Long> getRid,
                                                               Function<T, String> getTid) {
        return attributes.stream()
                .map(attribute -> Arrays.asList(
                        new AbstractMap.SimpleEntry<>(getName.apply(attribute) + "__rid", getRid.apply(attribute)),
                        new AbstractMap.SimpleEntry<>(getName.apply(attribute) + "__tid", getTid.apply(attribute)),
                        new AbstractMap.SimpleEntry<>(getName.apply(attribute) + "", getValue.apply(attribute))
                ))
                .flatMap(List::stream)
                .collect(HashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll);
    }

    public static <T> List<T> getAttributesFromMap(Map<String, Object> attrMap, Function<Attribute, T> getAttribute) {
        Map<String, Long> ridMap = new HashMap<>();
        Map<String, String> tidMap = new HashMap<>();
        Map<String, Object> valueMap = new HashMap<>();
        List<String> attrs = new ArrayList<>();

        attrMap.forEach((key, value) -> {
            if (key.endsWith("__rid")) {
                ridMap.put(key.replace("__rid", ""), Optional.ofNullable(value).map(v -> Long.valueOf("" + v)).orElse(null));
            } else if (key.endsWith("__tid")) {
                tidMap.put(key.replace("__tid", ""), (String) value);
            } else {
                valueMap.put(key, value);
                attrs.add(key);
            }
        });

        return attrs.stream()
                .map(attr -> {
                    Attribute attribute = new Attribute() {{ key = attr; value = valueMap.get(attr); rid = ridMap.get(attr); tid = tidMap.get(attr); }};
                    return getAttribute.apply(attribute);
                })
                .collect(Collectors.toList());
    }
}
