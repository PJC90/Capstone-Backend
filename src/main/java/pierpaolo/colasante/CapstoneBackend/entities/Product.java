package pierpaolo.colasante.CapstoneBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pierpaolo.colasante.CapstoneBackend.entities.enums.ProductType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({ "reviewProduct", "carts", "orders"})
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
    private LocalDate dateCreation;
    private String photo1;
    private String photo2;
    private String photo3;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @OneToMany(mappedBy = "productReview")
    private List<Review> reviewProduct;
    @ManyToMany(mappedBy = "productListCart")
    private List<Cart> carts;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
}
