package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ShopDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.ShopDAO;

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
    public void deleteShop(int shopId){
        Shop delete = this.findById(shopId);
        shopDAO.delete(delete);
        System.out.println("Shop eliminato");
    }
    public Shop saveShop(ShopDTO body){
        Shop newShop = new Shop();
        User foundSeller = userService.findById(body.userSellerId());
        newShop.setShopName(body.shopName());
        newShop.setSeller(foundSeller);
        newShop.setLogoShop("https://ui-avatars.com/api/?name=" + body.shopName());
        newShop.setCoverImageShop("https://ui-avatars.com/api/?name=" + body.shopName());
        return shopDAO.save(newShop);
    }
}
