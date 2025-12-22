package credit_wallet_system.demo.repositories.orderrepository;

import credit_wallet_system.demo.models.order.OrderVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderVo, Long> {

    Optional<OrderVo> findByOrderIdAndClientId(Long orderId, Long clientId);
}
