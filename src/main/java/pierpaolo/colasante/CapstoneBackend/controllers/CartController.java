package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.Cart;
import pierpaolo.colasante.CapstoneBackend.entities.Product;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ProductIdDTO;
import pierpaolo.colasante.CapstoneBackend.services.CartService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Cart> allCart(){return cartService.findAllCart();}
    @GetMapping("/{cartId}")
    public List<Product> allProductInCart(@PathVariable UUID cartId){
        return cartService.getAllProductInCart(cartId);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart saveCart(@AuthenticationPrincipal User user){
            return cartService.saveCart(user.getUserId());
    }
    @PostMapping("/{cartId}/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addProductToCart(@PathVariable UUID cartId, @RequestBody ProductIdDTO productId){
        return cartService.addProductToCart(cartId, productId);
    }

//    Per come è impostato il progetto non puoi eliminare il carrello per avere traccia nell'ordine la lista di prodotti acquistati
//    In teoria una volta fatto l'ordine se user vuole acquistare di nuovo deve creare un nuovo carrello
//    @DeleteMapping("/{cartId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteCart(@PathVariable UUID cartId){cartService.deleteCart(cartId);}

}
