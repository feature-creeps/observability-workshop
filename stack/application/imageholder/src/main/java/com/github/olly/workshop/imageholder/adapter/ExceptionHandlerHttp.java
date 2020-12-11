package com.github.olly.workshop.imageholder.adapter;

import com.github.olly.workshop.imageholder.service.EventService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    logAndAppendToEvent(ex);
    return new ResponseEntity<>(((ResponseStatusException) ex).getStatus());
  }

  @ExceptionHandler({Throwable.class})
  @ResponseBody
  ResponseEntity<Exception> handleThrowable(Exception ex) throws Exception {
    ex = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "unexpected exception thrown", ex);
    logAndAppendToEvent(ex);
    return new ResponseEntity<>(((ResponseStatusException) ex).getStatus());
  }

  private void logAndAppendToEvent(Throwable ex) {
    LOGGER.error(ex.getLocalizedMessage(), ex);

    eventService.addFieldToActiveEvent("response_status", ((ResponseStatusException) ex).getStatus());
    eventService.addFieldToActiveEvent("exception_thrown", "true");
    eventService.addFieldToActiveEvent("exception_message", ex.getMessage());
    eventService.addFieldToActiveEvent("exception_stacktrace", ExceptionUtils.getStackTrace(ex));
  }

}
