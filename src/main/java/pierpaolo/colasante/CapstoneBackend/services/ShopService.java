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
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ShopFullDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.ShopDAO;
import pierpaolo.colasante.CapstoneBackend.repositories.UserDAO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopService {
    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Cloudinary cloudinary;
    public Page<Shop> findAllShop(int size, int page, String order){
        Pageable pageable = PageRequest.of(size, page, Sort.by(order));
        return shopDAO.findAll(pageable);
    }
    public Shop findById(int shopId){
        return shopDAO.findById(shopId).orElseThrow(()->new NotFoundException(shopId));
    }
    public List<Shop> getShopByUserId(UUID userId){
        Optional<User> userOptional = userDAO.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user.getShopList();
        }else{
            throw new NotFoundException("Utente di id " + userId + " non trovato");
        }
    }
    public Shop saveShop(User user, ShopDTO body){
        System.out.println(body.shopName());
        UUID userId = user.getUserId();
        Shop newShop = new Shop();
        User foundSeller = userService.findById(userId);
        foundSeller.setRole(Roles.SELLER);
        newShop.setShopName(body.shopName());
        newShop.setSeller(foundSeller);
        newShop.setLogoShop("https://ui-avatars.com/api/?name=" + body.shopName() + "&length=9&font-size=0.15" + "&background=e4ba8e&color=fff");
        newShop.setCoverImageShop("https://ui-avatars.com/api/?name=shop&length=4&font-size=0.1" + "&background=e4ba8e&color=fff");
        return shopDAO.save(newShop);
    }
    public Shop updateShop(User user, int shopId, ShopFullDTO body){
        Shop existingShop = findById(shopId);
        if (!existingShop.getSeller().getUserId().equals(user.getUserId())) {
            throw new IllegalStateException("Non hai il permesso per modificare questo negozio...");
        }
        if (body.shopName() != null) {existingShop.setShopName(body.shopName());}
        if (body.description() != null) {existingShop.setDescription(body.description());}
        if (body.nation() != null) {existingShop.setNation(body.nation());}
        if (body.locality() != null) {existingShop.setLocality(body.locality());}
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
