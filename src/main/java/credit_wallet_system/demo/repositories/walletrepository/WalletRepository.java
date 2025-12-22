package credit_wallet_system.demo.repositories.walletrepository;

import credit_wallet_system.demo.models.wallet.WalletVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WalletRepository extends JpaRepository<WalletVo, Long> {
    Optional<WalletVo> findByClientId(Long clientId);
}
