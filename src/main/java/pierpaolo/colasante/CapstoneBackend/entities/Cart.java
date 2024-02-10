package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue
    private UUID cartId;
    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productListCart;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "cart")
    private Order order;
}
