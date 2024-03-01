package pierpaolo.colasante.CapstoneBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"shopReview", "orderReview"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;
    private int rating;
    private String photoReview;
    private LocalDate dateReview;
    private String description;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shopReview;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"shop","reviewProduct","carts","category","orders"})
    private Product productReview;
    @ManyToOne
    @JoinColumn(name = "user_buyer_id")
    private User buyerReview;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderReview;
}
