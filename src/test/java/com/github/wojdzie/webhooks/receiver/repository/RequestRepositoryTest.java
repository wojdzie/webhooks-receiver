package com.github.wojdzie.webhooks.receiver.repository;

import com.github.wojdzie.webhooks.receiver.model.Request;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestRepositoryTest {

    private RequestRepository requestRepository = new RequestRepository();

    @Test
    void shouldReturnAddedRequest() {
        Request request = mock(Request.class);

        requestRepository.addRequest(request);
        List<Request> results = requestRepository.getRequests();

        assertEquals(1, results.size());
        assertEquals(request, results.get(0));
    }

    @Test
    void shouldReturnEmptyList() {
        List<Request> results = requestRepository.getRequests();

        assertEquals(0, results.size());
    }

    @Test
    void shouldReturnListOfRequestsOrderByDateDESC() {
        Request firstRequest = mock(Request.class);
        Request secondRequest = mock(Request.class);
        Request thirdRequest = mock(Request.class);

        LocalDateTime baseTime = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        when(firstRequest.getCreationTime()).thenReturn(baseTime.plusSeconds(30));
        when(secondRequest.getCreationTime()).thenReturn(baseTime);
        when(thirdRequest.getCreationTime()).thenReturn(baseTime.minusSeconds(5));

        requestRepository.addRequest(firstRequest);
        requestRepository.addRequest(secondRequest);
        requestRepository.addRequest(thirdRequest);

        List<Request> requests = requestRepository.getRequests();

        assertEquals(requests.size(), 3);
        assertEquals(requests.get(0), firstRequest);
        assertEquals(requests.get(1), secondRequest);
        assertEquals(requests.get(2), thirdRequest);
    }
}