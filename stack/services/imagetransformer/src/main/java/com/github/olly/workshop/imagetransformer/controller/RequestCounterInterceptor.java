package com.github.olly.workshop.imagetransformer.controller;

import io.prometheus.client.Counter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class RequestCounterInterceptor extends HandlerInterceptorAdapter {

    private static final Counter requestTotal = Counter.build()
            .name("http_requests_total")
            .labelNames("method", "handler", "status", "path")
            .help("Http Request Total").register();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
            throws Exception {
        // Update counters
        String handlerLabel = handler.toString();
        // get short form of handler method name
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            handlerLabel = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        }
        requestTotal.labels(request.getMethod(), handlerLabel, Integer.toString(response.getStatus()), request.getServletPath()).inc();
    }
}
