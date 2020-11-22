package com.github.wojdzie.webhooks.receiver.controller;

import com.github.wojdzie.webhooks.receiver.model.Request;
import com.github.wojdzie.webhooks.receiver.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/request")
    public List<Request> getRequests() {
        return requestService.getRequests();
    }

    @RequestMapping(path = "/**")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void mockRequest(HttpServletRequest request) {
        String path = String.valueOf(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
        String method = request.getMethod();
        logger.info("Mocked {} request to path: {}", method, path);
    }
}
