package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ShopDTO;
import pierpaolo.colasante.CapstoneBackend.services.ShopService;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;
    @GetMapping
    public Page<Shop> shopList(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "shopId") String order){
        return shopService.findAllShop(page, size, order);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.CREATED)
    // diventi SELLER con put /seller senza passare niente nel body!
    public Shop saveNewShop(@AuthenticationPrincipal User user, @RequestBody ShopDTO payload){
        return shopService.saveShop(user, payload);
    }
    @PutMapping("/{shopId}")
    @PreAuthorize("hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Shop updateShop(@AuthenticationPrincipal User user, @PathVariable int shopId, @RequestBody ShopDTO payload){
        return shopService.updateShop(user, shopId, payload);
    }
    @DeleteMapping("/{shopId}")
    @PreAuthorize("hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShop(@AuthenticationPrincipal User user, @PathVariable int shopId) {
        shopService.deleteShop(user, shopId);
    }
}
