package com.example.ejb.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonConverter {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T fromFile(String filePath, Class<T> clazz) {
        try {
            File file = new File(filePath);
            return mapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo JSON: " + filePath, e);
        }
    }
}
