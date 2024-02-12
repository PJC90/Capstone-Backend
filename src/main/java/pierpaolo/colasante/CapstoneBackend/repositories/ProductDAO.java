package pierpaolo.colasante.CapstoneBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pierpaolo.colasante.CapstoneBackend.entities.Product;

import java.util.UUID;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID> {
}
