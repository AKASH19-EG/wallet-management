package credit_wallet_system.demo.dto.wallet;


import lombok.Data;

@Data
public class WalletTransactionRequest {
    private Long clientId;
    private Double amount;
}
