package pierpaolo.colasante.CapstoneBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.entities.enums.Roles;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ShopDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.ShopDAO;

import java.io.IOException;
import java.util.UUID;

@Service
public class ShopService {
    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private Cloudinary cloudinary;
    public Page<Shop> findAllShop(int size, int page, String order){
        Pageable pageable = PageRequest.of(size, page, Sort.by(order));
        return shopDAO.findAll(pageable);
    }
    public Shop findById(int shopId){
        return shopDAO.findById(shopId).orElseThrow(()->new NotFoundException(shopId));
    }

    public Shop saveShop(User user, ShopDTO body){
        System.out.println(body.shopName());
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
    public String uploadLogoShop(MultipartFile file, int shopId) throws IOException {
        Shop found = this.findById(shopId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setLogoShop(url);
        shopDAO.save(found);
        return url;
    }
    public String uploadCoverImageShop(MultipartFile file, int shopId) throws IOException {
        Shop found = this.findById(shopId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setCoverImageShop(url);
        shopDAO.save(found);
        return url;
    }
}
