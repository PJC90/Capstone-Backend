package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;
    private int rating; //@Min(1) e @Max(5), Validation nel DTO
    private String description;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shopReview;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productReview;
    @ManyToOne
    @JoinColumn(name = "user_buyer_id")
    private Buyer buyerReview;
}
