package pierpaolo.colasante.CapstoneBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pierpaolo.colasante.CapstoneBackend.entities.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID> {
        @Query("SELECT p FROM Product p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
        List<Product> findByTitleStartingWith(@Param("title") String title);

}
