package pierpaolo.colasante.CapstoneBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pierpaolo.colasante.CapstoneBackend.entities.enums.PaymentType;

import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"order"})
public class Payment {
    @Id
    @GeneratedValue
    private UUID paymentId;
    @OneToOne(mappedBy = "payment")
    private Order order;
    private String transactionCode;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private double total;
}
