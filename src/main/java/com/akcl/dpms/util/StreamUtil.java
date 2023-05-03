package com.akcl.dpms.util;

import java.util.Objects;
import java.util.stream.Stream;


public class StreamUtil {
    @SafeVarargs
    public static <T> T firstNonNull(T... items) throws NullPointerException {
        return Stream.of(items).filter(Objects::nonNull).findFirst().orElseThrow(NullPointerException::new);
    }
}
