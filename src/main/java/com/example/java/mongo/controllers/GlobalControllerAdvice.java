package com.example.java.mongo.controllers;

import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Error> handleResponseStatusException(ResponseStatusException e) {
    var code = e.getStatusCode().value();
    var title = HttpStatus.resolve(code).getReasonPhrase();
    var message = e.getReason();
    return ResponseEntity.status(code)
      .header("Content-Type", "application/json")
      .body(new Error(title, message));
  }

  @ExceptionHandler(ServletException.class)
  public ResponseEntity<Error> handleServletException(ServletException e) {
    if (e instanceof ErrorResponse) {
      var code = ((ErrorResponse) e).getStatusCode().value();
      var title = HttpStatus.resolve(code).getReasonPhrase();
      var message = e.getMessage();
      return ResponseEntity.status(code)
        .header("Content-Type", "application/json")
        .body(new Error(title, message));
    }
    return handleException(e);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Error> handleException(Exception e) {
    Error systemError = new Error("Something went wrong", e.getMessage());
    return ResponseEntity.status(500)
      .header("Content-Type", "application/json")
      .body(systemError);
  }

  record Error(String title, String message) {
  }
}
