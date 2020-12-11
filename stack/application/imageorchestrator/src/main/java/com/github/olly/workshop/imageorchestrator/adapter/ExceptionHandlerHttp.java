package com.github.olly.workshop.imageorchestrator.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerHttp {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerHttp.class);

  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  ResponseEntity<Exception> handleNotFoundException(Exception ex) throws Exception {
    LOGGER.error(ex.getLocalizedMessage(), ex);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({Throwable.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  ResponseEntity<Exception> handleThrowable(Exception ex) throws Exception {
    LOGGER.error(ex.getLocalizedMessage(), ex);
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
