package demo.orderservice.repository;

import demo.orderservice.model.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo,Long> {

    List<OrderInfo> findByConsumerId(Long consumerId);

}
