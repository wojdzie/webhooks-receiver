package com.github.wojdzie.webhooks.receiver.service;

import com.github.wojdzie.webhooks.receiver.model.Request;
import com.github.wojdzie.webhooks.receiver.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private RequestRepository requestRepository;

    private final Logger logger = LoggerFactory.getLogger(RequestService.class);

    RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getRequests() {
        return requestRepository.getRequests();
    }

    public boolean handleRequest(HttpServletRequest request) {
        boolean result;
        try {
            Request requestToHandle = createRequest(request);
            result = addRequest(requestToHandle);

        } catch (Exception e) {
            logger.error("Request could not be created for reason: ", e);
            result = false;
        }
        return result;

    }

    private boolean addRequest(Request request) {
        return requestRepository.addRequest(request);
    }

    private Request createRequest(HttpServletRequest request) throws IOException {
        HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);

        String requestBody = readRequestBody(requestCacheWrapperObject);
        Map<String, List<String>> requestHeaders = readRequestHeaders(requestCacheWrapperObject);
        Map<String, List<String>> requestParameters = readRequestParameters(requestCacheWrapperObject);
        String method = requestCacheWrapperObject.getMethod();

        return Request.builder()
                .method(method)
                .body(requestBody)
                .creationTime(LocalDateTime.now())
                .parameters(requestParameters)
                .headers(requestHeaders)
                .build();
    }

    private Map<String, List<String>> readRequestParameters(HttpServletRequest requestCacheWrapperObject) {
        Map<String, String[]> parameterMap = requestCacheWrapperObject.getParameterMap();

        return parameterMap.entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entry -> List.of(entry.getValue())
                ));
    }

    private Map<String, List<String>> readRequestHeaders(HttpServletRequest requestCacheWrapperObject) {
        return Collections
                .list(requestCacheWrapperObject.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(Function.identity(),
                        headers -> Collections.list(requestCacheWrapperObject.getHeaders(headers)))
                );
    }

    private String readRequestBody(HttpServletRequest requestCacheWrapperObject) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = requestCacheWrapperObject.getInputStream().read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8);
    }
}
