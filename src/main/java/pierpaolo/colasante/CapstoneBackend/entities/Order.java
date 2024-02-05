package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pierpaolo.colasante.CapstoneBackend.entities.enums.OrderType;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private OrderType orderTypeStatus;
    @OneToMany(mappedBy = "productOrder")
    private List<Product> productListOrder;
}
