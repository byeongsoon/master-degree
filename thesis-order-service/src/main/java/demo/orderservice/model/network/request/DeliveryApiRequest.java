package demo.orderservice.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryApiRequest {

    private Long id;

    private String status;

    private String revAddress;

    private String revName;

}
