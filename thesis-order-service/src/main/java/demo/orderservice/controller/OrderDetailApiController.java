package demo.orderservice.controller;

import demo.orderservice.model.entity.OrderDetail;
import demo.orderservice.model.network.request.OrderDetailApiRequest;
import demo.orderservice.model.network.response.OrderDetailApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order/detail")
public class OrderDetailApiController extends CrudController<OrderDetailApiResponse, OrderDetailApiRequest, OrderDetail> {
}
