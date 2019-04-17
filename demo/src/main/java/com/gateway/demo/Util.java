package com.gateway.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Model1;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class Util {

    static String tryOr(ObjectMapper mapper, Model1 value, Supplier<String> other) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return other.get();
        }
    }

}
