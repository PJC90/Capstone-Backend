package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Buyer extends User{
    @OneToMany(mappedBy = "buyerReview")
    private List<Review> reviewBuyerList;
}
