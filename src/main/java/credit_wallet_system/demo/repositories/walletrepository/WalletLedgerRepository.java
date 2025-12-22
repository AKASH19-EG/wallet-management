package credit_wallet_system.demo.repositories.walletrepository;

import credit_wallet_system.demo.models.wallet.WalletLedgerVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletLedgerRepository extends JpaRepository<WalletLedgerVo, Long> {
}
