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
    public Cart saveCart(UUID userId){
        Cart newCart = new Cart();
        User user = userService.findById(userId);
        newCart.setUser(user);
        return cartDAO.save(newCart);
    }
    public Cart addProductToCart(UUID cartId, ProductIdDTO productId){
        Cart cart = cartDAO.findById(cartId).orElseThrow(()->new NotFoundException(cartId));
        Product foundProduct = productService.findById(productId.productId());
        if(foundProduct != null) {
            cart.getProductListCart().add(foundProduct);
           return cartDAO.save(cart);
        }else{
            throw new NotFoundException(productId.productId());
        }
    }
}
