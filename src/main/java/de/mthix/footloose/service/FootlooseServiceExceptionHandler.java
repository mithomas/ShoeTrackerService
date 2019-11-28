package de.mthix.footloose.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.NoSuchElementException;


interface NoSuchElementAdviceTrait extends AdviceTrait {

  @ExceptionHandler
  default ResponseEntity<Problem> handleNoSuchElementException(final NoSuchElementException exception, final NativeWebRequest request) {
    return create(Status.NOT_FOUND, exception, request);
  }
}

/**
 * https://github.com/jovannypcg/exception_handler/blob/2068f984657a45daed93f8b1230fca3302f94bae/src/main/java/mx/jovannypcg/exceptionhandler/controller/exception/GlobalExceptionHandler.java
 * t
 */
@ControllerAdvice
public class FootlooseServiceExceptionHandler implements ProblemHandling, NoSuchElementAdviceTrait {

  @Override
  public boolean isCausalChainsEnabled() {
    return true;
  }
}
