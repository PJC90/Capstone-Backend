package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue
    private UUID cartId;
    @OneToMany(mappedBy = "productCart")
    private List<Product> productListCart;
}