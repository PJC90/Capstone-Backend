package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.exceptions.BadRequestException;
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
    public Shop saveNewShop(@AuthenticationPrincipal User user, @RequestBody @Validated ShopDTO payload, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors());
        } else{
            return shopService.saveShop(user, payload);
        }
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
    @PatchMapping("/{shopId}/photoshop")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhotoShop(@RequestParam("photo") MultipartFile file, @PathVariable int shopId) throws Exception{
        return shopService.uploadLogoShop(file, shopId);
    }
    @PatchMapping("/{shopId}/covershop")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadCoverShop(@RequestParam("cover") MultipartFile file, @PathVariable int shopId) throws Exception{
        return shopService.uploadCoverImageShop(file, shopId);
    }
}
