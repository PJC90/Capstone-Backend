package pierpaolo.colasante.CapstoneBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String nameplate;
    @OneToOne(mappedBy = "category")
    private Product productCategory;


}
