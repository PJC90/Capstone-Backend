package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.Order;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.services.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Order> orderList(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "orderId") String order){
        return orderService.findAllOrder(page, size, order);
    }
    @PostMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Order saveOrder(@AuthenticationPrincipal User user,@PathVariable UUID paymentId){
            return orderService.saveOrder(user.getUserId(), paymentId);
    }

}
