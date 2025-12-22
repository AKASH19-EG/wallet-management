package credit_wallet_system.demo.dto.wallet;


import lombok.Data;

@Data
public class WalletBalanceResponse {
    private Long clientId;
    private Double balance;
}
