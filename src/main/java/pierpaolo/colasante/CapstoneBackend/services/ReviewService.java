package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.*;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ReviewDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.ReviewDAO;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    public Page<Review> findAllReview(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return reviewDAO.findAll(pageable);
    }
    public Review findById(int reviewId){
        return reviewDAO.findById(reviewId).
                orElseThrow(()->new NotFoundException(reviewId));}

    public Review saveReview( ReviewDTO body){
        Shop shop = shopService.findById(body.shop_id());
        Product product = productService.findById(body.product_id());
        User user = userService.findById(body.user_buyer_id());
        Order order = orderService.findById(body.order_id());
        boolean isOrdered = orderService.isUserOrderedProductInShop(String.valueOf(user.getUserId()), String.valueOf(product.getProductId()), String.valueOf(shop.getShopId()));
        if(!isOrdered) {
            throw new IllegalStateException("L'utente non ha effettuato un ordine per questo prodotto in questo negozio");
        }
            Review newReview = new Review();
            newReview.setRating(body.rating());
            newReview.setDescription(body.description());
            newReview.setShopReview(shop);
            newReview.setProductReview(product);
            newReview.setBuyerReview(user);
            newReview.setOrderReview(order);
            return reviewDAO.save(newReview);
    }
    public Review updateReview(int reviewId, ReviewDTO body){
        Review foundReview = this.findById(reviewId);
        Shop shop = shopService.findById(body.shop_id());
        Product product = productService.findById(body.product_id());
        User user = userService.findById(body.user_buyer_id());
        Order order = orderService.findById(body.order_id());
        foundReview.setRating(body.rating());
        foundReview.setDescription(body.description());
        foundReview.setShopReview(shop);
        foundReview.setProductReview(product);
        foundReview.setBuyerReview(user);
        foundReview.setOrderReview(order);
        return reviewDAO.save(foundReview);
    }
    public void deleteReview(int reviewId){
        Review foundreview = this.findById(reviewId);
        reviewDAO.delete(foundreview);
    }
    public List<Review> filterByShop(int id){
        return reviewDAO.filterByShop(id);
    }

}
