package com.github.wojdzie.webhooks.receiver.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Request {

    private final String method;

    private final String body;

    private final LocalDateTime creationTime;

    private final Map<String, List<String>> parameters;

    private final Map<String, List<String>> headers;

    private Request(RequestBuilder builder) {
        this.method = builder.method;
        this.body = builder.body;
        this.creationTime = builder.creationTime;
        this.parameters = builder.parameters;
        this.headers = builder.headers;
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    static class RequestBuilder {

        private String method;

        private String body;

        private LocalDateTime creationTime;

        private Map<String, List<String>> parameters;

        private Map<String, List<String>> headers;

        public RequestBuilder method(String method) {
            this.method = method;
            return this;
        }

        public RequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public RequestBuilder creationTime(LocalDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public RequestBuilder parameters(Map<String, List<String>> parameters) {
            this.parameters = parameters;
            return this;
        }

        public RequestBuilder headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
