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
    public Order saveOrder(OrderDTO body){
        User user = userService.findById(body.user_id());
        Cart cart = cartService.findById(body.cart_id());
        Order newOrder = new Order();
        newOrder.setUserId(user);
        newOrder.setCart(cart);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatusOrder(StatusOrder.IN_PROGRESS);
        return orderDAO.save(newOrder);
    }
    public Order findById(UUID orderId){
        return orderDAO.findById(orderId).
                orElseThrow(()->new NotFoundException(orderId));}
    public boolean isUserOrderedProductInShop(String userId, String productId, String shopId) {
        // Converti shopId da String a int
        int shopIdInt = Integer.parseInt(shopId);

        // Ottieni gli ordini dell'utente
        List<Order> userOrders = orderDAO.findByUserId(UUID.fromString(userId));

        // Scansiona gli ordini dell'utente per cercare una corrispondenza
        for (Order order : userOrders) {
            // Scansiona la lista dei prodotti in quell'ordine
            for (Product product : order.getProductListOrder()) {
                // Verifica se uno dei prodotti in quell'ordine corrisponde all'ID del prodotto fornito
                if (product.getProductId().equals(productId)) {
                    // Verifica se il negozio associato al prodotto corrisponde all'ID del negozio fornito
                    if (product.getShop().getShopId() == shopIdInt) {
                        // Se trovi una corrispondenza, restituisci true
                        return true;
                    }
                }
            }
        }
        // Se non trovi alcuna corrispondenza, restituisci false
        return false;
    }
}
