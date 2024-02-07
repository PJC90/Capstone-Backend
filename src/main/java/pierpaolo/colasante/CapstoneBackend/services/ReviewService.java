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
        Review newReview = new Review();
        newReview.setRating(body.rating());
        newReview.setDescription(body.description());
        newReview.setShopReview(shop);
        newReview.setProductReview(product);
        newReview.setBuyerReview(user);
        return reviewDAO.save(newReview);
    }
}
