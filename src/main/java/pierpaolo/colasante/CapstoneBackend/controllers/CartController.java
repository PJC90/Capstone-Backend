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

    @PostMapping("/addproduct")
    @ResponseStatus(HttpStatus.CREATED)
    // se non esiste crea un nuovo Carrello e aggiunge prodotti
    // se esiste aggiunge prodotti al carrello esistente
    public Cart addProductToCart(@AuthenticationPrincipal User user, @RequestBody ProductIdDTO productId){
        return cartService.addProductToCart(user.getUserId(), productId);
    }

//    Per come è impostato il progetto non puoi eliminare il carrello
//    (puoi solo rimuovere i prodotti dal carrello!)
//    i prodotti del carrello una volta pagati, vanno a finire in order per avere uno storico di prodotti acquistati
//    quindi la lista di prodotti nel carrello si svuota, e il carrello è pronto per essere riempito di nuovo

    @DeleteMapping("/removeproduct")
    @ResponseStatus(HttpStatus.OK)
    public Cart removeProductFromCart(@AuthenticationPrincipal User user, @RequestBody ProductIdDTO productId){
    return cartService.removeProductFromCart(user.getUserId(), productId);
    }
}
