package pierpaolo.colasante.CapstoneBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pierpaolo.colasante.CapstoneBackend.entities.Cart;

import java.util.UUID;
@Repository
public interface CartDAO extends JpaRepository<Cart, UUID> {
}
