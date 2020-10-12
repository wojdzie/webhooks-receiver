package com.github.wojdzie.webhooks.receiver.repository;

import com.github.wojdzie.webhooks.receiver.model.Request;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Repository
public class RequestRepository {

    private final List<Request> requests = new ArrayList<>();

    public boolean addRequest(Request request) {
        return requests.add(request);
    }

    public List<Request> getRequests() {
        sortRequestsByDateDesc();
        return requests;
    }

    private void sortRequestsByDateDesc() {
        final Comparator<Request> requestsByDateComparator = getRequestsByDateComparator();
        final Comparator<Request> requestsByDateReversedComparator = requestsByDateComparator.reversed();
        requests.sort(requestsByDateReversedComparator);
    }

    private Comparator<Request> getRequestsByDateComparator() {
        final Function<Request, LocalDateTime> getCreationTime = Request::getCreationTime;
        return Comparator.comparing(getCreationTime);
    }


}
