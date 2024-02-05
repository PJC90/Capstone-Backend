package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pierpaolo.colasante.CapstoneBackend.entities.enums.ProductType;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    private UUID productId;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    private double price;
    private int quantity;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Categories category;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @OneToMany(mappedBy = "productReview")
    private List<Review> reviewProduct;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart productCart;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order productOrder;
    private String photo1;
    private String photo2;
    private String photo3;
}
