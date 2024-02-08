package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ReviewDTO;
import pierpaolo.colasante.CapstoneBackend.services.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @GetMapping
    public Page<Review> reviewsList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "shopId") String order){
        return reviewService.findAllReview(page, size, order);
    }
    @GetMapping("/filter")
    // forse è @GetMapping("/filter/shopId")
    public List<Review> filterReviewByShop(@RequestParam(name = "shopId") int id){
        return reviewService.filterByShop(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review saveReview(@RequestBody ReviewDTO payload){
        return reviewService.saveReview(payload);
    }
}
