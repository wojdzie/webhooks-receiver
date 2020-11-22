package com.github.wojdzie.webhooks.receiver.service;

import com.github.wojdzie.webhooks.receiver.model.Request;
import com.github.wojdzie.webhooks.receiver.repository.RequestRepository;
import com.github.wojdzie.webhooks.receiver.util.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestService requestService = new RequestService(requestRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void when_requestIsCorrect_then_handleRequestShouldReturnTrue() throws IOException {
        // given
        ServletInputStream requestBodyInputStream = getServletInputSteamMock();
        Map<String, List<String>> headers = TestDataGenerator.prepareHeaders();
        Map<String, String[]> parameters = TestDataGenerator.prepareParametersWithValueArray();
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        Request request = mock(Request.class);

        // when
        when(servletRequest.getInputStream()).thenReturn(requestBodyInputStream);
        when(servletRequest.getHeaderNames()).thenReturn(Collections.enumeration(headers.keySet()));
        when(servletRequest.getHeaders("content-type")).thenReturn(Collections.enumeration(headers.get("content-type")));
        when(servletRequest.getHeaders("host")).thenReturn(Collections.enumeration(headers.get("host")));
        when(servletRequest.getHeaders("accept")).thenReturn(Collections.enumeration(headers.get("accept")));
        when(servletRequest.getHeaders("cache-control")).thenReturn(Collections.enumeration(headers.get("cache-control")));
        when(servletRequest.getParameterMap()).thenReturn(parameters);
        when(requestRepository.addRequest(any())).thenReturn(true);
        when(requestRepository.getRequests()).thenReturn(List.of(request));

        boolean result = requestService.handleRequest(servletRequest);

        // then
        assertTrue(result);
        assertNotNull(requestService.getRequests());
        assertEquals(requestService.getRequests().size(), 1);
    }

    @Test
    void when_requestIsNull_then_handleRequestShouldReturnFalse() {
        // when
        boolean result = requestService.handleRequest(null);
        // then
        assertFalse(result);
    }

    @Test
    void when_requestIsCorrectButReadingStreamThrowsIOException_then_handleRequestShouldReturnFalse() throws IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        // when
        when(request.getInputStream()).thenThrow(new IOException());
        boolean result = requestService.handleRequest(request);
        // then
        assertFalse(result);
    }

    @Test
    void when_requestIsEmpty_then_handleRequestShouldReturnTrue() throws IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletInputStream requestBodyInputStream = getServletInputSteamMock();
        Map<String, List<String>> headers = new HashMap<>();
        // when
        when(request.getInputStream()).thenReturn(requestBodyInputStream);
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(headers.keySet()));
        when(requestRepository.addRequest(any())).thenReturn(true);

        boolean result = requestService.handleRequest(request);
        // then
        assertTrue(result);
    }

    private ServletInputStream getServletInputSteamMock() {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return -1;
            }
        };
    }
}