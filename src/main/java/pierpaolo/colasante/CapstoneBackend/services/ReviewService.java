package pierpaolo.colasante.CapstoneBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.*;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ReviewDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.ReviewDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private Cloudinary cloudinary;

    public Page<Review> findAllReview(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return reviewDAO.findAll(pageable);
    }
    public Review findById(int reviewId){
        return reviewDAO.findById(reviewId).
                orElseThrow(()->new NotFoundException(reviewId));}

    public Review saveReview(UUID userId, ReviewDTO body){
        Shop shop = shopService.findById(body.shop_id());
        Product product = productService.findById(body.product_id());
        User user = userService.findById(userId);
        Order order = orderService.findById(body.order_id());
        // Controlla se l'utente ha già lasciato una recensione per lo stesso prodotto in questo ordine
        if (reviewDAO.existsByOrderReviewAndProductReviewAndBuyerReview(order, product, user)) {
            throw new IllegalStateException("L'utente ha già lasciato una recensione per questo prodotto in questo ordine");
        }
        boolean isOrdered = orderService.isUserOrderedProductInShop(user, product, shop);
        if(!isOrdered) {
            throw new IllegalStateException("L'utente non ha effettuato un ordine per questo prodotto in questo negozio");
        }
            Review newReview = new Review();
            newReview.setRating(body.rating());
            newReview.setPhotoReview("https://ui-avatars.com/api/?name=" + shop.getShopName());
            newReview.setDateReview(LocalDate.now());
            newReview.setDescription(body.description());
            newReview.setShopReview(shop);
            newReview.setProductReview(product);
            newReview.setBuyerReview(user);
            newReview.setOrderReview(order);
            return reviewDAO.save(newReview);
    }
    public Review findReviewIfOrderedProductExists(UUID userId, UUID productId, int shopId, UUID orderId) {
        Shop shop = shopService.findById(shopId);
        Product product = productService.findById(productId);
        User user = userService.findById(userId);
        // Controlla se l'utente ha effettuato un ordine per questo prodotto in questo negozio
        boolean isOrdered = orderService.isUserOrderedProductInShop(user, product, shop);
        if (!isOrdered) {
            throw new IllegalStateException("L'utente non ha effettuato un ordine per questo prodotto in questo negozio");
        }
        // Filtra le recensioni in base all'ID del prodotto
        List<Review> reviews = reviewDAO.filterByProduct(productId);

        // Trova la recensione corrispondente all'utente nell'elenco filtrato
        Optional<Review> existingReview = reviews.stream()
                .filter(review -> review.getBuyerReview().equals(user))
                .filter(review -> review.getOrderReview().getOrderId().equals(orderId)) // Aggiunto controllo sull'ID dell'ordine
                .findFirst();

        return existingReview.orElse(null);
    }
    public List<Review> filterByShop(int id){
        return reviewDAO.filterByShop(id);
    }
    public List<Review> filterByProduct(UUID id){return reviewDAO.filterByProduct(id);}
    public String uploadPhotoReview(MultipartFile file, int reviewId) throws IOException {
        Review found = this.findById(reviewId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setPhotoReview(url);
        reviewDAO.save(found);
        return url;
    }
}
