package com.github.olly.workshop.imagethumbnail.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerHttp {

  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  String handleNotFoundException(Exception ex) {
    return "not found";
  }

  @ExceptionHandler({Throwable.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  String handleThrowable(Exception ex) {
    return "internal server error";
  }

}
