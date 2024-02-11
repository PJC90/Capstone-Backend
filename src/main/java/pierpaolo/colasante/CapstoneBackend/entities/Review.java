package pierpaolo.colasante.CapstoneBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"shopReview", "productReview", "buyerReview", "orderReview"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;
    private int rating;
    private String description;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shopReview;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productReview;
    @ManyToOne
    @JoinColumn(name = "user_buyer_id")
    private User buyerReview;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order orderReview;
}
