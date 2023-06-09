package org.test.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

@RestController
@RequestMapping({"/hello","/health", "/live"})
public class HelloController {

  @PreDestroy
  private void shutdown() throws InterruptedException {
    System.out.println("hello controller bean destroy");
    Thread.sleep(5 * 1000);
    System.out.println("hello bean grace period over");
  }

  @GetMapping
  public ResponseEntity hello() throws InterruptedException {
    Thread.sleep(500);
    return ResponseEntity.ok().build();
  }
}
