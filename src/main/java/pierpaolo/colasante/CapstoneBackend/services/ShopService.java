package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.entities.enums.Roles;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ShopDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.ShopDAO;

import java.util.UUID;

@Service
public class ShopService {
    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private UserService userService;
    public Page<Shop> findAllShop(int size, int page, String order){
        Pageable pageable = PageRequest.of(size, page, Sort.by(order));
        return shopDAO.findAll(pageable);
    }
    public Shop findById(int shopId){
        return shopDAO.findById(shopId).orElseThrow(()->new NotFoundException(shopId));
    }

    public Shop saveShop(User user, ShopDTO body){
        UUID userId = user.getUserId();
        Shop newShop = new Shop();
        User foundSeller = userService.findById(userId);
        if(foundSeller.getRole() != Roles.SELLER){
            throw new IllegalStateException("Solo i SELLER posso creare nuovi negozi...");
        }
        newShop.setShopName(body.shopName());
        newShop.setSeller(foundSeller);
        newShop.setLogoShop("https://ui-avatars.com/api/?name=" + body.shopName());
        newShop.setCoverImageShop("https://ui-avatars.com/api/?name=" + body.shopName());
        return shopDAO.save(newShop);
    }
    public Shop updateShop(User user, int shopId, ShopDTO body){
        Shop existingShop = findById(shopId);
        if (!existingShop.getSeller().getUserId().equals(user.getUserId())) {
            throw new IllegalStateException("Non hai il permesso per modificare questo negozio...");
        }
        if (body.shopName() != null) {
            existingShop.setShopName(body.shopName());
        }
        return shopDAO.save(existingShop);
    }
    public void deleteShop(User user, int shopId){
        Shop existingShop = findById(shopId);
        if (!existingShop.getSeller().getUserId().equals(user.getUserId())) {
            throw new IllegalStateException("Non hai il permesso per eliminare questo negozio.");
        }
        shopDAO.delete(existingShop);
    }
}
