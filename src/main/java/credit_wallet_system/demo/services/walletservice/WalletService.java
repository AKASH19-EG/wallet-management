package credit_wallet_system.demo.services.walletservice;


import credit_wallet_system.demo.exceptions.wallet.InsufficientBalanceException;
import credit_wallet_system.demo.exceptions.wallet.WalletNotFoundException;
import credit_wallet_system.demo.models.wallet.WalletLedgerVo;
import credit_wallet_system.demo.models.wallet.WalletVo;
import credit_wallet_system.demo.repositories.walletrepository.WalletLedgerRepository;
import credit_wallet_system.demo.repositories.walletrepository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    WalletLedgerRepository walletLedgerRepository;

    @Transactional
    public WalletVo creditWallet(Long clientId, Double amount) {
        WalletVo wallet = getOrCreateWallet(clientId);
        wallet.setAvailableBalance(wallet.getAvailableBalance() + amount);
        wallet = walletRepository.save(wallet);

        // Create ledger entry
        WalletLedgerVo ledger = new WalletLedgerVo();
        ledger.setWalletVo(wallet);
        ledger.setClientId(clientId);
        ledger.setAmount(amount);
        ledger.setType("CREDIT");
        walletLedgerRepository.save(ledger);

        return wallet;
    }

    private WalletVo getOrCreateWallet(Long clientId) {
        Optional<WalletVo> walletOpt = walletRepository.findByClientId(clientId);
        if (walletOpt.isPresent()) {
            return walletOpt.get();
        }

        WalletVo wallet = new WalletVo();
        wallet.setClientId(clientId);
        wallet.setAvailableBalance(0.0);
        return walletRepository.save(wallet);
    }


    @Transactional
    public WalletVo debitWallet(Long clientId, Double amount) {
        WalletVo wallet = walletRepository.findByClientId(clientId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for client: " + clientId));

        if (wallet.getAvailableBalance() < amount) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: " + wallet.getAvailableBalance() + ", Required: " + amount
            );
        }

        wallet.setAvailableBalance(wallet.getAvailableBalance() - amount);
        wallet = walletRepository.save(wallet);

        // Create ledger entry
        WalletLedgerVo ledger = new WalletLedgerVo();
        ledger.setWalletVo(wallet);
        ledger.setClientId(clientId);
        ledger.setAmount(amount);
        ledger.setType("DEBIT");
        walletLedgerRepository.save(ledger);

        return wallet;
    }

    public Double getBalance(Long clientId) {
        WalletVo wallet = walletRepository.findByClientId(clientId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for client: " + clientId));
        return wallet.getAvailableBalance();
    }

}
