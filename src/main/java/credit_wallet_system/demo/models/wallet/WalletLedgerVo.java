package credit_wallet_system.demo.models.wallet;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "wallet_ledger")
@Data
public class WalletLedgerVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_ledger_id", length = 10)
    private long walletLedgerId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id")
    WalletVo walletVo;

    @Column(name = "client_id", length = 10)
    private long clientId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type", length = 20)
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_on", length = 50, updatable = false)
    private Date createdOn;

}
