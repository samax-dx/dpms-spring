package com.akcl.dpms.util;

import java.util.concurrent.Callable;
import java.util.function.Consumer;


public class ArgUtil {
    public static <T> T accept(T arg, Consumer<T> argConsumer) {
        argConsumer.accept(arg);
        return arg;
    }

    public static <T> T computeSafe(Callable<T> op) {
        try {
            return op.call();
        } catch (Exception ignore) {
            return null;
        }
    }

    public static class Value<T> {
        private T value;
        public static <T> Value<T> of(T data) {
            Value<T> value = new Value<>();
            value.set(data);
            return value;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }
    }
}
