package pierpaolo.colasante.CapstoneBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pierpaolo.colasante.CapstoneBackend.entities.Order;
import pierpaolo.colasante.CapstoneBackend.entities.Product;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.entities.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewDAO extends JpaRepository<Review, Integer> {
    @Query("SELECT i FROM Review i WHERE i.shopReview.shopId= :id")
    List<Review> filterByShop(@Param("id") int id);
    @Query("SELECT i FROM Review i WHERE i.productReview.productId= :id")
    List<Review> filterByProduct(@Param("id") UUID id);

    boolean existsByOrderReviewAndProductReviewAndBuyerReview(Order orderReview, Product productReview, User buyerReview);

}
