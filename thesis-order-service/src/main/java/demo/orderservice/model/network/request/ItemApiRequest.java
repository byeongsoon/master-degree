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
public class ItemApiRequest {

    private Long id;

    private String status;

    private String name;

    private String content;

    private BigDecimal price;

    private String brandName;

}
