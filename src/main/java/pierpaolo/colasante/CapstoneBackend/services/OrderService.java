package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.*;
import pierpaolo.colasante.CapstoneBackend.entities.enums.StatusOrder;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.OrderDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.CartDAO;
import pierpaolo.colasante.CapstoneBackend.repositories.OrderDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    public Page<Order> findAllOrder(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return orderDAO.findAll(pageable);
    }
    public Order saveOrder(UUID userId, OrderDTO body){
        User user = userService.findById(userId);
        Cart cart = cartService.findById(body.cart_id());
        Order newOrder = new Order();
        newOrder.setUserId(user);
        newOrder.setCart(cart);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatusOrder(StatusOrder.IN_PROGRESS);
        // Spostamento dei prodotti dal carrello all'ordine
        List<Product> productsInCart = cart.getProductListCart();
        for(Product product : productsInCart) {
            newOrder.addProduct(product);
        }
        // Rimozione dei prodotti dal carrello
        cart.getProductListCart().clear();
        // Salvataggio dell'ordine con i prodotti
        return orderDAO.save(newOrder);
    }
    public Order findById(UUID orderId){
        return orderDAO.findById(orderId).
                orElseThrow(()->new NotFoundException(orderId));}
    public boolean isUserOrderedProductInShop(User user, Product product, Shop shop) {

        // Ottieni gli ordini dell'utente
        List<Order> userOrders = orderDAO.findByUserId(user.getUserId());

        // Scansiona gli ordini dell'utente per cercare una corrispondenza
        for (Order order : userOrders) {
            // Ottieni il carrello associato all'ordine
            Cart cart = order.getCart();

            // Verifica se il carrello è valido
            if (cart != null) {
                // Scansiona la lista dei prodotti nel carrello
                for (Product productscan : cart.getProductListCart()) {
                    // Verifica se uno dei prodotti nel carrello corrisponde all'ID del prodotto fornito
                    if (productscan.getProductId().equals(product.getProductId())) {
                        // Verifica se il negozio associato al prodotto corrisponde all'ID del negozio fornito
                        if (productscan.getShop().getShopId() == shop.getShopId()) {
                            // Se trovi una corrispondenza, restituisci true
                            return true;
                        }
                    }
                }
            }
        }
        // Se non trovi alcuna corrispondenza, restituisci false
        return false;
    }

}
