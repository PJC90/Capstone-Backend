package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Cart;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.repositories.CartDAO;

import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;
    public Page<Cart> findAllCart(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return cartDAO.findAll(pageable);
    }
    public Cart findById(UUID cartId){
        return cartDAO.findById(cartId).
                orElseThrow(()->new NotFoundException(cartId));}
}
