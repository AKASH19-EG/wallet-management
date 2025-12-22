package credit_wallet_system.demo.controllers.wallet;


import credit_wallet_system.demo.dto.api.ApiResponse;
import credit_wallet_system.demo.dto.orders.CreateOrderRequest;
import credit_wallet_system.demo.dto.orders.OrderResponse;
import credit_wallet_system.demo.dto.wallet.WalletBalanceResponse;
import credit_wallet_system.demo.exceptions.orders.FulfillmentApiException;
import credit_wallet_system.demo.exceptions.wallet.InsufficientBalanceException;
import credit_wallet_system.demo.exceptions.wallet.WalletNotFoundException;
import credit_wallet_system.demo.models.order.OrderVo;
import credit_wallet_system.demo.services.orderservice.OrderService;
import credit_wallet_system.demo.services.walletservice.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    WalletService walletService;

    @Autowired
    OrderService orderService;

    @GetMapping("/wallet/balance")
    public ResponseEntity<ApiResponse> getWalletBalance(@RequestHeader("client-id") Long clientId) {
        try {
            Double balance = walletService.getBalance(clientId);
            WalletBalanceResponse response = new WalletBalanceResponse();
            response.setClientId(clientId);
            response.setBalance(balance);
            return ResponseEntity.ok(new ApiResponse(true,"Balance retrieved successfully", response));
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse> createOrder(
            @RequestHeader("client-id") Long clientId,
            @RequestBody CreateOrderRequest request) {
        try {
            OrderVo order = orderService.createOrder(clientId, request.getAmount());
            OrderResponse response = mapToOrderResponse(order);
            return ResponseEntity.ok(new ApiResponse(true,"Order Created successfully", response));
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (FulfillmentApiException e) {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }


    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse> getOrder(
            @RequestHeader("client-id") Long clientId,
            @PathVariable Long orderId) {
        try {
            OrderVo order = orderService.getOrder(orderId, clientId);
            OrderResponse response = mapToOrderResponse(order);
            return ResponseEntity.ok(new ApiResponse(true,"Order retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }


    private OrderResponse mapToOrderResponse(OrderVo order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setClientId(order.getClientId());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus());
        response.setFulfillmentId(order.getFulfillmentId());
        response.setCreatedOn(order.getCreatedOn());
        return response;
    }

}
