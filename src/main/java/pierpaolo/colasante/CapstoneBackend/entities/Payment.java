package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pierpaolo.colasante.CapstoneBackend.entities.enums.PaymentType;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue
    private UUID paymentId;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String transactionCode;
    private PaymentType paymentType;
}
