package com.github.olly.workshop.imagegrayscale.controller;


import com.github.olly.workshop.imagegrayscale.service.EventService;
import com.github.olly.workshop.imagegrayscale.service.MetricsService;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    MetricsService metricsService;

    private static final String startedAt = "startedAt";

    @Autowired
    EventService eventService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        eventService.newEvent();
        eventService.addFieldToActiveEvent(startedAt, LocalDateTime.now());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        // Update counters
        metricsService.httpRequestReceived(request.getMethod(), request.getContextPath(), Integer.toString(response.getStatus()), request.getServletPath());


        Map<String, Object> fields = new HashMap<String, Object>();
        fields.putAll(extractRequestFields(request));
        fields.putAll(extractResponseFields(response));

        final LocalDateTime now = LocalDateTime.now();
        if (eventService.getFieldFromActiveEvent(startedAt) != null) {
            final Long duration = Duration.between((LocalDateTime) eventService.getFieldFromActiveEvent(startedAt), now).toMillis();
            fields.put("duration_ms", duration);
        }
        fields.put("finishedAt", now);
        eventService.addFieldsToActiveEvent(fields);
        eventService.publishEvent(request.getMethod() + " request to " + request.getRequestURI());
    }

    private Map<? extends String, ?> extractResponseFields(HttpServletResponse response) {
        Map<String, Object> fields = new HashMap<>();

        fields.put("response.status", response.getStatus());
        response.getHeaderNames().forEach(header ->
                fields.put("response.header." + header, response.getHeader(header)));

        return fields;
    }

    private Map<String, Object> extractRequestFields(HttpServletRequest request) {
        Map<String, Object> fields = new HashMap<>();

        fields.put("request.authType", request.getAuthType());
        fields.put("request.contextPath", request.getContextPath());
        if (request.getCookies() != null) {
            fields.put("request.cookies.exist", true);
            Arrays.asList(request.getCookies()).forEach(cookie ->
                    fields.put("request.cookies." + cookie.getName(), cookie.getValue()));
        } else {
            fields.put("request.cookies.exist", false);
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String header = headerNames.nextElement();
            fields.put("request.header." + header, request.getHeader(header));
        }

        fields.put("request.method", request.getMethod());
        fields.put("request.pathInfo", request.getPathInfo());
        fields.put("request.queryString", request.getQueryString());
        fields.put("request.remoteUser", request.getRemoteUser());
        fields.put("request.requestedSessionId", request.getRequestedSessionId());
        fields.put("request.requestUri", request.getRequestURI());
        fields.put("request.servletPath", request.getServletPath());

        return fields;
    }
}
