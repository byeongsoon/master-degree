package demo.deliveryservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery")
public class TestController {

  @GetMapping("")
  public String getDeliveryTest() {
    return "Delivery Service!!";
  }

}
