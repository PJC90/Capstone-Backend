package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pierpaolo.colasante.CapstoneBackend.entities.enums.StatusOrder;

import java.time.LocalDateTime;
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
    private StatusOrder statusOrder;
    private LocalDateTime orderDate;
    @OneToMany(mappedBy = "productOrder")
    private List<Product> productListOrder;
    @OneToOne(mappedBy = "order")
    private Payment payment;
    @OneToOne(mappedBy = "orderReview")
    private Review review;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
