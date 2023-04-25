package com.github.olly.workshop.springevents.adapter;

import com.github.olly.workshop.springevents.service.Event;
import com.github.olly.workshop.springevents.service.EventService;
import com.github.olly.workshop.springevents.service.MetricsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    MetricsService metricsService;

    @Autowired
    EventService eventService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        eventService.newEvent(Event.EventTrigger.HTTP);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        try {
            Map<String, Object> fields = new HashMap<String, Object>();

            fields.putAll(extractRequestFields(request));
            fields.putAll(extractResponseFields(response));

            registerException(e, fields);

            eventService.addFieldsToActiveEvent(fields);
        } finally {
            eventService.publishEvent(request.getMethod() + " request to " + request.getRequestURI());

            // Update request counter
            metricsService.httpRequestReceived(request.getMethod(), request.getContextPath(), Integer.toString(response.getStatus()), request.getServletPath());
        }
    }

    private void registerException(Exception e, Map<String, Object> fields) {
        if (e != null) {
            fields.put("exception_thrown", "true");
            fields.put("exception_message", e.getMessage());
            fields.put("exception_stacktrace", ExceptionUtils.getStackTrace(e));
        } else {
            final Object exceptionThrown_o = eventService.getFieldFromActiveEvent("exception_thrown");
            final Boolean exceptionThrown;
            if (exceptionThrown_o != null) {
                if (exceptionThrown_o instanceof Boolean) {
                    exceptionThrown = (Boolean) exceptionThrown_o;
                } else if (exceptionThrown_o instanceof String) {
                    exceptionThrown = Boolean.valueOf((String) exceptionThrown_o);
                } else {
                    exceptionThrown = false;
                }
            } else {
                exceptionThrown = false;
            }
            fields.put("exception_thrown", exceptionThrown);
        }
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

        // set cookies
        String cookieHeader = request.getHeader("cookie");
        if (!StringUtils.isEmpty(cookieHeader)) {
            fields.put("request.cookies.exist", true);
            String[] cookies = cookieHeader.split(";");
            Arrays.asList(cookies).forEach(cookie -> {
                String cookieName = cookie.split("=")[0];
                String cookieValue = cookie.split("=")[1];
                fields.put("request.cookies." + simplify(cookieName), simplify(cookieValue));
                if (cookieName.trim().equals("user")) {
                    fields.put("user", cookieValue);
                }
            });
        } else {
            fields.put("request.cookies.exist", false);
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String header = headerNames.nextElement();
            fields.put("request.header." + header, request.getHeader(header));
            if (header.equalsIgnoreCase("X-Real-IP")) {
                fields.put("remote_address", request.getHeader(header));
            }
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

    private String simplify(String in) {
        return in.replaceAll("\\W", "_");
    }
}
