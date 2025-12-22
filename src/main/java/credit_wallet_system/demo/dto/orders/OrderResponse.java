package credit_wallet_system.demo.dto.orders;


import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {
    private Long orderId;
    private Long clientId;
    private Double amount;
    private String status;
    private String fulfillmentId;
    private Date createdOn;
}
