package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shopId;
    private String shopName;
    private String logoShop;
    private String coverImageShop;
    private int numberOfSales;
    @ManyToOne
    @JoinColumn(name = "user_seller_id")
    private User seller;
    @OneToMany(mappedBy = "shop")
    private List<Product> productList;
    @OneToMany(mappedBy = "shopReview")
    private List<Review> reviewList;
}
