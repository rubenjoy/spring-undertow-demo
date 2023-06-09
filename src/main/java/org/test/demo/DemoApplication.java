package org.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
public class DemoApplication implements ApplicationListener<ContextClosedEvent> {

	public static void main(String[] args) {
    Runtime.getRuntime()
            .addShutdownHook(new Thread() {
              @Override
              public void run() {
                System.out.println("VM shutdown hook");
                try {
                  Thread.sleep(15 * 1000);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                System.out.println("VM shutdown grace period over");
              }
            });

		SpringApplication.run(DemoApplication.class, args);
	}

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    System.out.println("context-closed event catched");
    try {
      Thread.sleep(15 * 1000);
    } catch (InterruptedException e) {
      System.err.println("sleep interrupted");
    }
    System.out.println("context-closed grace period over");
  }
}
