package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String nameCategory;
    @OneToMany(mappedBy = "category")
    private List<Product> product;
}
