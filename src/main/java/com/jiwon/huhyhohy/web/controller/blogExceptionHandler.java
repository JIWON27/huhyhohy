package com.jiwon.huhyhohy.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RestController
public class blogExceptionHandler {

  @ExceptionHandler
  public String globalExceptionHandler(Exception e){
    return "<h1>" + e.getMessage() + "</h1>";
  }
}
