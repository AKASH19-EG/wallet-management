package credit_wallet_system.demo.controllers.wallet;

import credit_wallet_system.demo.dto.api.ApiResponse;
import credit_wallet_system.demo.dto.wallet.WalletTransactionRequest;
import credit_wallet_system.demo.exceptions.wallet.InsufficientBalanceException;
import credit_wallet_system.demo.exceptions.wallet.WalletNotFoundException;
import credit_wallet_system.demo.models.wallet.WalletVo;
import credit_wallet_system.demo.services.walletservice.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminController {

    @Autowired
    WalletService walletService;

    @PostMapping("/wallet/credit")
    public ResponseEntity<ApiResponse> creditWallet(@RequestBody WalletTransactionRequest creditRequest) {
        try {
            WalletVo wallet = walletService.creditWallet(creditRequest.getClientId(), creditRequest.getAmount());
            return ResponseEntity.ok(new ApiResponse(true,"Wallet credited successfully", wallet));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));

        }
    }

    @PostMapping("/wallet/debit")
    public ResponseEntity<ApiResponse> debitWallet(@RequestBody WalletTransactionRequest request) {
        try {
            WalletVo wallet = walletService.debitWallet(request.getClientId(), request.getAmount());
            return ResponseEntity.ok(new ApiResponse(true,"Wallet debited successfully", wallet));
        } catch (InsufficientBalanceException | WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
