package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Order;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.repositories.OrderDAO;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderDAO orderDAO;
    public Page<Order> findAllOrder(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return orderDAO.findAll(pageable);
    }
    public Order findById(UUID orderId){
        return orderDAO.findById(orderId).
                orElseThrow(()->new NotFoundException(orderId));}
}
