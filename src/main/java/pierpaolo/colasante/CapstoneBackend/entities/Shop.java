package pierpaolo.colasante.CapstoneBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"productList", "reviewList"})
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shopId;
    private String shopName;
    private String description;
    private String nation;
    private String locality;
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

    public void incrementNumberOfSales(int increment) {
        this.numberOfSales += increment;
    }
}
