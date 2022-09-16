package demo.orderservice.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemApiResponse {

    private Long id;

    private String status;

    private String name;

    private String content;

    private BigDecimal price;

    private String brandName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
