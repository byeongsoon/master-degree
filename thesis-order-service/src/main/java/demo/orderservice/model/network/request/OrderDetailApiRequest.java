package demo.orderservice.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailApiRequest {

    private Long id;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Long orderInfoId;

    private Long itemId;

}
