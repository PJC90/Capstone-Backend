package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Product;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.repositories.ProductDAO;

import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;
    public Page<Product> findAllProduct(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return productDAO.findAll(pageable);
    }
    public Product findById(UUID productId){
        return productDAO.findById(productId).
                orElseThrow(()->new NotFoundException(productId));}
}
