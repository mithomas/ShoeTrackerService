package de.mthix.footloose.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class FootlooseServiceExceptionHandlerTest {

  private ResponseEntity<Problem> responseEntity;

  @Nested
  class NoSuchElement {

    @BeforeEach
    void callHandler() {
      responseEntity = new NoSuchElementAdviceTrait() {}.handleNoSuchElementException(new NoSuchElementException("missing element"), null);
    }

    @Test
    void problem() {
      Problem problem = Problem.builder()
          .withStatus(Status.NOT_FOUND)
          .withTitle("Element could not be found.")
          .withDetail("missing element")
          .build();
      assertThat(responseEntity.getBody()).isEqualTo(problem);
    }

    @Test
    void httpStatus() {
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
  }
}