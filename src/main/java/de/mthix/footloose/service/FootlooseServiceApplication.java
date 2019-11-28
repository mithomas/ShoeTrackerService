package de.mthix.footloose.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@MapperScan("de.mthix.footloose.service.dao")
public class FootlooseServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(FootlooseServiceApplication.class, args);
  }

  @PostConstruct
  public void init() {
    // force application into UTC timezone to avoid offset errors with dates and database
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}
