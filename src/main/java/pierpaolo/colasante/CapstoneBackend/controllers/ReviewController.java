package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.exceptions.BadRequestException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ReviewDTO;
import pierpaolo.colasante.CapstoneBackend.services.ReviewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @GetMapping
    public Page<Review> reviewsList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "rating") String order){
        return reviewService.findAllReview(page, size, order);
    }
    @GetMapping("/filter")
    public List<Review> filterReviewByShop(@RequestParam(name = "shopId") int id){
        return reviewService.filterByShop(id);
    }
    @GetMapping("/filterproduct")
    public List<Review> filterReviewByProduct(@RequestParam(name = "productId") UUID id){
        return reviewService.filterByProduct(id);
    }
    @GetMapping("/filterReview")
    public Review filterReviewProductBuy(@AuthenticationPrincipal User user,
                                         @RequestParam(name = "productId") UUID productId,
                                         @RequestParam(name = "shopId") int shopId,
                                         @RequestParam(name = "orderId") UUID orderId){
        return reviewService.findReviewIfOrderedProductExists(user.getUserId(), productId, shopId, orderId );
    }
    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable int reviewId){
        return reviewService.findById(reviewId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review saveReview(@AuthenticationPrincipal User user, @RequestBody @Validated ReviewDTO payload, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            return reviewService.saveReview(user.getUserId(), payload);
        }
    }
    @PatchMapping("/{reviewId}/photoreview")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhotoReview(@RequestParam("photo") MultipartFile file, @PathVariable int reviewId) throws Exception{
        return reviewService.uploadPhotoReview(file, reviewId);
    }
}
