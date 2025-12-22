package credit_wallet_system.demo.dto.fulfillmentapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FulfillmentResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;
}
