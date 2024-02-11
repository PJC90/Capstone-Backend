package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Cart;
import pierpaolo.colasante.CapstoneBackend.entities.Product;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ProductIdDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.CartDAO;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    public List<Cart> findAllCart(){return cartDAO.findAll();}
    public List<Product> getAllProductInCart(UUID cartId){
        Cart cart = cartDAO.findById(cartId).orElseThrow(()->new NotFoundException(cartId));
        return cart.getProductListCart();
    }
    public Cart findById(UUID cartId){
        return cartDAO.findById(cartId).
                orElseThrow(()->new NotFoundException(cartId));}
    public void deleteCart(UUID cartId){
        Cart found = this.findById(cartId);
        cartDAO.delete(found);
    }
    public Cart addProductToCart(UUID userId, ProductIdDTO productId){
        User user = userService.findById(userId);
        Cart existingCart = cartDAO.findByUser(user);
        if(existingCart == null){
            existingCart = new Cart();
            existingCart.setUser(user);
        }
        Product foundProduct = productService.findById(productId.productId());
        if(foundProduct != null) {
            existingCart.getProductListCart().add(foundProduct);
           return cartDAO.save(existingCart);
        }else{
            throw new NotFoundException(productId.productId());
        }
    }
}
