package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Cart;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CartDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.CartDAO;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private UserService userService;
    public List<Cart> findAllCart(){return cartDAO.findAll();}
    public Cart findById(UUID cartId){
        return cartDAO.findById(cartId).
                orElseThrow(()->new NotFoundException(cartId));}
    public void deleteCart(UUID cartId){
        Cart found = this.findById(cartId);
        cartDAO.delete(found);
    }
    public Cart saveCart(CartDTO body){
        Cart newCart = new Cart();
        User user = userService.findById(body.user_id());
        newCart.setUser(user);
        return cartDAO.save(newCart);
    }
}
