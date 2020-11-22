package com.github.wojdzie.webhooks.receiver.config;

import com.github.wojdzie.webhooks.receiver.service.RequestService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    private final RequestService requestService;

    public RequestInterceptor(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return requestService.handleRequest(request);
    }
}