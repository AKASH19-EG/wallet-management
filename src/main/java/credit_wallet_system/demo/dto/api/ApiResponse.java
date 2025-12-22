package credit_wallet_system.demo.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    @JsonProperty(value = "status")
    private boolean status;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "response")
    private Object response;

}
