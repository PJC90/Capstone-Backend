package pierpaolo.colasante.CapstoneBackend.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.*;
import pierpaolo.colasante.CapstoneBackend.entities.enums.StatusOrder;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.repositories.OrderDAO;

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
    @Autowired
    private PaymentService paymentService;
    public Page<Order> findAllOrder(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return orderDAO.findAll(pageable);
    }
    public Order findById(UUID orderId){
        return orderDAO.findById(orderId).
                orElseThrow(()->new NotFoundException(orderId));}
    @Transactional
    public Order saveOrder(UUID userId, UUID paymentId){
        User user = userService.findById(userId);
        Cart cart = cartService.findById(user.getCart().getCartId());
        Payment payment = paymentService.findById(paymentId);
        Order newOrder = new Order();
        newOrder.setUserId(user);
        newOrder.setCart(cart);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatusOrder(StatusOrder.DELIVERED);
        newOrder.setPayment(payment);
        // Spostamento dei prodotti dal carrello all'ordine
        List<Product> productsInCart = cart.getProductListCart();
        for(Product product : productsInCart) {
            newOrder.addProduct(product);
        }
        // Rimozione dei prodotti dal carrello
        cart.getProductListCart().clear();
        // Salvataggio dell'ordine con i prodotti
        Order savedOrder = orderDAO.save(newOrder);
        savedOrder.updateShopSales();
        return savedOrder;
    }

    public boolean isUserOrderedProductInShop(User user, Product product, Shop shop) {
        // Ottieni gli ordini completati dell'utente
        List<Order> userCompletedOrders = orderDAO.findByUserId(user.getUserId());

        // Scansiona gli ordini completati dell'utente
        for (Order order : userCompletedOrders) {
            // Verifica se l'ordine contiene il prodotto specificato nel negozio specificato
            if (order.getProducts().contains(product) && product.getShop().equals(shop)) {
                return true;
            }
        }
        return false;
    }

}
