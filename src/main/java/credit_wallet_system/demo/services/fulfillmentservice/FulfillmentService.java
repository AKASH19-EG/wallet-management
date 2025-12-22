package credit_wallet_system.demo.services.fulfillmentservice;


import credit_wallet_system.demo.dto.fulfillmentapi.FulfillmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FulfillmentService {

    @Value("${fulfillment_api_url}")
    private String FULFILLMENT_API_URL;

    @Autowired
    RestTemplate restTemplate;


    public String createFulfillment(Long clientId, Long orderId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("userId", clientId);
            requestBody.put("title", String.valueOf(orderId));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<FulfillmentResponse> response = restTemplate.exchange(
                    FULFILLMENT_API_URL,
                    HttpMethod.POST,
                    request,
                    FulfillmentResponse.class
            );

            // checking here code series should be in 200 series
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return String.valueOf(response.getBody().getId());
            }

            throw new RuntimeException("Fulfillment API returned unsuccessful response");
        } catch (Exception e) {
            throw new RuntimeException("Failed to call fulfillment API: " + e.getMessage(), e);
        }
    }


}
