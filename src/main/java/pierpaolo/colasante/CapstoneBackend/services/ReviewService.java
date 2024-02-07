package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.repositories.ReviewDAO;

@Service
public class ReviewService {
    @Autowired
    private ReviewDAO reviewDAO;
    public Page<Review> findAllReview(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return reviewDAO.findAll(pageable);
    }
    public Review findById(int reviewId){
        return reviewDAO.findById(reviewId).
                orElseThrow(()->new NotFoundException(reviewId));}

}
