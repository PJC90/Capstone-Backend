package pierpaolo.colasante.CapstoneBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pierpaolo.colasante.CapstoneBackend.entities.Order;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderDAO extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o JOIN o.userId u WHERE u.userId = :userId")
    List<Order> findByUserId(@Param("userId") UUID userId);
}
