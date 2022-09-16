package demo.orderservice.service;

import demo.orderservice.model.entity.OrderDetail;
import demo.orderservice.model.network.Header;
import demo.orderservice.model.network.Pagination;
import demo.orderservice.model.network.request.OrderDetailApiRequest;
import demo.orderservice.model.network.response.OrderDetailApiResponse;
import demo.orderservice.repository.ItemRepository;
import demo.orderservice.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailApiLogicService extends BaseService<OrderDetailApiResponse, OrderDetailApiRequest, OrderDetail> {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .quantity(body.getQuantity())
                .totalPrice(body.getTotalPrice())
                .orderInfo(orderInfoRepository.getById(body.getOrderInfoId()))
                .item(itemRepository.getById(body.getItemId()))
                .build();

        return Header.OK(response(baseRepository.save(orderDetail)));
    }

    public void orderByConsumer(OrderDetailApiRequest request) {
        OrderDetail orderDetail = OrderDetail.builder()
            .quantity(request.getQuantity())
            .totalPrice(request .getTotalPrice())
            .orderInfo(orderInfoRepository.getById(request.getOrderInfoId()))
            .item(itemRepository.getById(request.getItemId()))
            .build();

        baseRepository.save(orderDetail);
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest body = request.getData();

        return baseRepository.findById(body.getId())
                .map(orderDetail -> orderDetail
                        .setQuantity(body.getQuantity())
                        .setTotalPrice(body.getTotalPrice())
                        .setOrderInfo(orderInfoRepository.getById(body.getOrderInfoId()))
                        .setItem(itemRepository.getById(body.getItemId()))
                )
                .map(orderDetail -> baseRepository.save(orderDetail))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("수정 실패"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(orderDetail -> {
                    baseRepository.delete(orderDetail);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("삭제 실패"));
    }

    @Override
    public Header<List<OrderDetailApiResponse>> search(Pageable pageable) {
        Page<OrderDetail> orderDetails = baseRepository.findAll(pageable);

        List<OrderDetailApiResponse> orderDetailApiResponseList = orderDetails.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPage(orderDetails.getTotalPages())
                .totalElements(orderDetails.getTotalElements())
                .currentPage(orderDetails.getNumber())
                .currentElements(orderDetails.getNumberOfElements())
                .build();

        return Header.OK(orderDetailApiResponseList,pagination);
    }

    private OrderDetailApiResponse response(OrderDetail orderDetail) {
        return OrderDetailApiResponse.builder()
                .id(orderDetail.getId())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .createdAt(orderDetail.getCreatedAt())
                .updatedAt(orderDetail.getUpdatedAt())
                .orderInfoId(orderDetail.getOrderInfo().getId())
                .itemId(orderDetail.getItem().getId())
                .build();
    }

}
