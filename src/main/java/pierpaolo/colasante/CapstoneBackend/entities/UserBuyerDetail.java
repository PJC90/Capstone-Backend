package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UserBuyerDetail {
    @Id
    @GeneratedValue
    private UUID buyerId;
    private LocalDate registerDate;
    private String Street;
    private int houseNumber;
    private int postalCode;
    private String city;
    private String province;
    private String comune;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User buyerDetail;
}
