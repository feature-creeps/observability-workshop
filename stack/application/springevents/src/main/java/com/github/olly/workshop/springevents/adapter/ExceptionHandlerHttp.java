package com.github.olly.workshop.springevents.adapter;

import com.github.olly.workshop.springevents.service.EventService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlerHttp {

    private final EventService eventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerHttp.class);

    public ExceptionHandlerHttp(EventService eventService) {
        this.eventService = eventService;
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    ResponseEntity<Exception> handleNotFoundException(Exception ex) throws Exception {
        ResponseStatusException rex = new ResponseStatusException(HttpStatus.NOT_FOUND, "not found", ex);
        logAndAppendToEvent(rex);
        return new ResponseEntity<>(rex.getStatusCode());
    }

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    ResponseEntity<Exception> handleThrowable(Exception ex) throws Exception {
        ResponseStatusException rex = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "unexpected exception thrown", ex);
        logAndAppendToEvent(rex);
        return new ResponseEntity<>((rex).getStatusCode());
    }

    private void logAndAppendToEvent(ResponseStatusException ex) {
        LOGGER.error(ex.getLocalizedMessage(), ex);

        eventService.addFieldToActiveEvent("response_status", ex.getStatusCode());
        eventService.addFieldToActiveEvent("exception_thrown", "true");
        eventService.addFieldToActiveEvent("exception_message", ex.getMessage());
        eventService.addFieldToActiveEvent("exception_stacktrace", ExceptionUtils.getStackTrace(ex));
    }

}
