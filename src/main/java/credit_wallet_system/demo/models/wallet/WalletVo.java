package credit_wallet_system.demo.models.wallet;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "wallet")
@Data
public class WalletVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id", length = 10)
    private long walletId;

    @Column(name = "client_id", length = 10)
    private long clientId;

    @Column(name = "available_balance")
    private double availableBalance;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_on", length = 50, updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "modified_on", length = 50)
    private Date modifiedOn;

    @JsonBackReference
    @OneToMany(mappedBy = "walletVo", cascade = CascadeType.ALL)
    private List<WalletLedgerVo> walletLedgerVo;
}
