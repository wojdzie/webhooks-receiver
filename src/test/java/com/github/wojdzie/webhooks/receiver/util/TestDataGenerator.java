package com.github.wojdzie.webhooks.receiver.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataGenerator {

    public static Map<String, List<String>> prepareParametersWithValueList() {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("param1", List.of("value1", "value2", "value3"));
        parameters.put("param2", List.of("value4"));

        return parameters;
    }

    public static Map<String, String[]> prepareParametersWithValueArray() {
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("param1", new String[]{"value1", "value2", "value3"});
        parameters.put("param2", new String[]{"value4"});

        return parameters;
    }

    public static Map<String, List<String>> prepareHeaders() {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("content-type", List.of("application/json"));
        headers.put("host", List.of("localhost:8080"));
        headers.put("accept", List.of("text/plain", "text/html"));
        headers.put("cache-control", List.of("no-cache"));

        return headers;
    }
}
