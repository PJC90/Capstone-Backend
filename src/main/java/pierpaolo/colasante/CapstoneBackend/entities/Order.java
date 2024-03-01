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
@JsonIgnoreProperties({"review", "cart"})
public class Order {
    @Id
    @GeneratedValue
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;
    private LocalDateTime orderDate;
    @OneToOne
    @JoinColumn(name = "payment_id")
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
    // PostPersist PostUpdate -> verrà eseguito dopo che un ordine è stato persistito o aggiornato nel database.
    @PostPersist
    @PostUpdate
    public void updateShopSales() {
        if (this.products != null && this.userId != null) {
            for (Product product : this.products) {
                Shop shop = product.getShop();
                if (shop != null) {
                    shop.incrementNumberOfSales(1);
                }
            }
        }
    }
}
