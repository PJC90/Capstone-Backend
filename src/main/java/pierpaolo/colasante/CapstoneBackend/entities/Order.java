package pierpaolo.colasante.CapstoneBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pierpaolo.colasante.CapstoneBackend.entities.enums.StatusOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"cart"})
public class Order {
    @Id
    @GeneratedValue
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;
    private LocalDateTime orderDate;
    @OneToOne(mappedBy = "order")
    private Payment payment;
    @OneToMany(mappedBy = "orderReview")
    private List<Review> review;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public void addProduct(Product product) {
        if(this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(product);
    }
}
