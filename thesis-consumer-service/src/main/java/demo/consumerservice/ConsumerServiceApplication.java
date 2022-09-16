package demo.consumerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerServiceApplication.class, args);
  }

}
