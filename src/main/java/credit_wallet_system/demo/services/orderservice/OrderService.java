package credit_wallet_system.demo.services.orderservice;


import credit_wallet_system.demo.exceptions.orders.FulfillmentApiException;
import credit_wallet_system.demo.models.order.OrderVo;
import credit_wallet_system.demo.repositories.orderrepository.OrderRepository;
import credit_wallet_system.demo.services.fulfillmentservice.FulfillmentService;
import credit_wallet_system.demo.services.walletservice.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    @Autowired
    WalletService walletService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FulfillmentService fulfillmentService;


    @Transactional
    public OrderVo createOrder(Long clientId, Double amount) {
        // validate  and deductt from wallit
        walletService.debitWallet(clientId, amount);

        // Create order
        OrderVo order = new OrderVo();
        order.setClientId(clientId);
        order.setAmount(amount);
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        // Here calling fulfillment api
        try {
            String fulfillmentId = fulfillmentService.createFulfillment(clientId, order.getOrderId());
            order.setFulfillmentId(fulfillmentId);
            order.setStatus("COMPLETED");
        } catch (Exception e) {
            // fulfilment failed then credit to wallit again
            walletService.creditWallet(clientId, amount);
            order.setStatus("FAILED");
            throw new FulfillmentApiException("Failed to create fulfillment: " + e.getMessage());
        }

        return orderRepository.save(order);
    }

    public OrderVo getOrder(Long orderId, Long clientId) {
        return orderRepository.findByOrderIdAndClientId(orderId, clientId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
