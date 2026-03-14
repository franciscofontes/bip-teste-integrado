package com.example.ejb.utils;

import com.example.ejb.converter.JsonConverter;

public class MockUtils {

    public static final String PATH = "src/test/resources/mocks/";

    public static <T> T fromFile(String fileName, Class<T> clazz) {
        return JsonConverter.fromFile(PATH + fileName, clazz);
    }
}
