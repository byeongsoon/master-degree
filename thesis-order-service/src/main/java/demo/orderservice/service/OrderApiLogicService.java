package demo.orderservice.service;

import demo.orderservice.model.entity.OrderInfo;
import demo.orderservice.model.network.Header;
import demo.orderservice.model.network.Pagination;
import demo.orderservice.model.network.request.DeliveryApiRequest;
import demo.orderservice.model.network.request.OrderDetailApiRequest;
import demo.orderservice.model.network.request.OrderInfoApiRequest;
import demo.orderservice.model.network.response.OrderInfoApiResponse;
import demo.orderservice.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderApiLogicService extends BaseService<OrderInfoApiResponse, OrderInfoApiRequest, OrderInfo>{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderDetailApiLogicService orderDetailApiLogicService;

    @Override
    public Header<OrderInfoApiResponse> create(Header<OrderInfoApiRequest> request) {
        OrderInfoApiRequest orderApiRequest = request.getData();
        OrderDetailApiRequest orderDetailApiRequest = orderApiRequest.getOrderDetail();

        OrderInfo order = OrderInfo.builder()
                .status(orderApiRequest.getStatus())
                .paymentType(orderApiRequest.getPaymentType())
                .totalPrice(orderApiRequest.getTotalPrice())
                .totalQuantity(orderApiRequest.getTotalQuantity())
                .orderAt(LocalDateTime.now())
                .deliveryId(orderApiRequest.getDeliveryId())
                .consumerId(orderApiRequest.getConsumerId())
                .build();

        OrderInfo orderInfo = baseRepository.save(order);

        orderDetailApiRequest.setOrderInfoId(orderInfo.getId());
        orderDetailApiLogicService.orderByConsumer(orderDetailApiRequest);

        return Header.OK(response(orderInfo));
    }

    @Override
    public Header<OrderInfoApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderInfoApiResponse> update(Header<OrderInfoApiRequest> request) {
        OrderInfoApiRequest orderApiRequest = request.getData();

        return baseRepository.findById(orderApiRequest.getId())
                .map(order -> order
                        .setStatus(orderApiRequest.getStatus())
                        .setPaymentType(orderApiRequest.getPaymentType())
                        .setTotalPrice(orderApiRequest.getTotalPrice())
                        .setTotalQuantity(orderApiRequest.getTotalQuantity())
                        .setDeliveryId(orderApiRequest.getDeliveryId())
                        .setConsumerId(orderApiRequest.getConsumerId())
                )
                .map(newOrder -> baseRepository.save(newOrder))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("수정 실패"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(order -> {
                    baseRepository.delete(order);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("삭제 실패"));
    }

    @Override
    public Header<List<OrderInfoApiResponse>> search(Pageable pageable) {
        Page<OrderInfo> orders = baseRepository.findAll(pageable);

        List<OrderInfoApiResponse> orderApiResponseList = orders.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPage(orders.getTotalPages())
                .totalElements(orders.getTotalElements())
                .currentPage(orders.getNumber())
                .currentElements(orders.getNumberOfElements())
                .build();

        return Header.OK(orderApiResponseList,pagination);
    }

    public Header<List<OrderInfoApiResponse>> getByConsumerId(Long id) {
        List<OrderInfoApiResponse> orderInfos = orderInfoRepository.findByConsumerId(id).stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(orderInfos);
    }

    // RestTemplate 이용하여 Delivery Service 에 POST 요청 후 Delivery Id를 받아옴
    public Header<OrderInfoApiResponse> orderByConsumer(Header<OrderInfoApiRequest> request) {
        OrderInfoApiRequest body = request.getData();

        String deliveryUrl = "http://localhost:8000/api/delivery/byOrder";
        DeliveryApiRequest deliveryApiRequest = DeliveryApiRequest.builder()
                .status(body.getDeliveryStatus())
                .revName(body.getRevName())
                .revAddress(body.getRevAddress())
                .build();

        Long deliveryID = restTemplate.postForObject(deliveryUrl,deliveryApiRequest,Long.class);
        request.getData().setDeliveryId(deliveryID);

        return create(request);
    }


    public OrderInfoApiResponse response(OrderInfo order) {
        return OrderInfoApiResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .paymentType(order.getPaymentType())
                .totalPrice(order.getTotalPrice())
                .totalQuantity(order.getTotalQuantity())
                .orderAt(order.getOrderAt())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .deliveryId(order.getDeliveryId())
                .consumerId(order.getConsumerId())
                .build();
    }

}
