package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Entity
@Getter
@Setter
public class Seller extends User{
    @OneToMany(mappedBy = "seller")
    private List<Shop> shopList;
}
