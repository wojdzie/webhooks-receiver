package com.github.wojdzie.webhooks.receiver.model;


import com.github.wojdzie.webhooks.receiver.model.Request.RequestBuilder;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class RequestTest {

    @Test
    void shouldReturnRequest() {
        RequestBuilder builder = Request.builder();
        Request request = builder.build();

        assertNotNull(request);
    }

    @Test
    void shouldReturnRequestMethod() {
        String method = "GET";
        RequestBuilder builder = Request.builder();

        Request request = builder.method(method).build();

        assertNotNull(request.getMethod());
        assertEquals(method, request.getMethod());
    }

    @Test
    void shouldReturnRequestBody() {
        String body = "{}";
        RequestBuilder builder = Request.builder();

        Request request = builder.body(body).build();

        assertNotNull(request.getBody());
        assertEquals(body, request.getBody());
    }

    @Test
    void shouldReturnRequestCreationTime() {
        LocalDateTime creationTime = LocalDateTime.now();
        RequestBuilder builder = Request.builder();

        Request request = builder.creationTime(creationTime).build();

        assertNotNull(request.getCreationTime());
        assertEquals(creationTime, request.getCreationTime());
    }

    @Test
    void shouldReturnRequestHeaders() {
        Map<String, List<String>> headers = createMap();
        RequestBuilder builder = Request.builder();

        Request request = builder.headers(headers).build();

        assertNotNull(request.getHeaders());
        assertEquals(headers, request.getHeaders());
    }

    @Test
    void shouldReturnRequestParameters() {
        Map<String, List<String>> parameters = createMap();
        RequestBuilder builder = Request.builder();

        Request request = builder.parameters(parameters).build();

        assertNotNull(request.getParameters());
        assertEquals(parameters, request.getParameters());
    }

    private Map<String, List<String>> createMap() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("key", List.of("value"));

        return map;
    }
}