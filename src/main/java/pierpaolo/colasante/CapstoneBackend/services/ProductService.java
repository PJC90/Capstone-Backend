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
import pierpaolo.colasante.CapstoneBackend.entities.*;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ProductDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.ProductDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private Cloudinary cloudinary;
    public Page<Product> findAllProduct(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return productDAO.findAll(pageable);
    }
    public Product findById(UUID productId){
        return productDAO.findById(productId).
                orElseThrow(()->new NotFoundException(productId));}

    public List<Product> findByTitleStartWith(String title){
        return productDAO.findByTitleStartingWith(title);
    }
    public Product saveProduct(ProductDTO body){
        Product newProduct = new Product();
        Shop shop = shopService.findById(body.shop_id());
        Category category= categoryService.findById(body.category_id());
        newProduct.setTitle(body.title());
        newProduct.setDescription(body.description());
        newProduct.setPrice(body.price());
        newProduct.setQuantity(body.quantity());
        newProduct.setDateCreation(LocalDate.now());
        newProduct.setShop(shop);
        newProduct.setCategory(category);
        newProduct.setProductType(body.productType());
        newProduct.setPhoto1("https://ui-avatars.com/api/?name=" + "Art"+ "&length=3&font-size=0.15" + "&background=e4ba8e&color=fff");
        newProduct.setPhoto2("https://ui-avatars.com/api/?name=" + "Ergo"+ "&length=4&font-size=0.15" + "&background=e4ba8e&color=fff");
        newProduct.setPhoto3("https://ui-avatars.com/api/?name=" + "Sum"+ "&length=3&font-size=0.15" + "&background=e4ba8e&color=fff");
        return productDAO.save(newProduct);
    }
    public Product updateProduct(UUID productId, ProductDTO body){
        Product updateProduct = this.findById(productId);
        if(body.title() != null){updateProduct.setTitle(body.title());}
        if(body.description() != null){updateProduct.setDescription(body.description());}
        if(body.price() != 0){updateProduct.setPrice(body.price());}
        if(body.quantity() != 0){updateProduct.setQuantity(body.quantity());}
        if(body.shop_id() != 0){
            Shop shop = shopService.findById(body.shop_id());
            updateProduct.setShop(shop);}
        if(body.category_id() != 0){
            Category category= categoryService.findById(body.category_id());
            updateProduct.setCategory(category);}
        if(body.productType() != null){updateProduct.setProductType(body.productType());}
        return productDAO.save(updateProduct);
    }
    public void deleteProduct(UUID productId){
        Product found = this.findById(productId);
        productDAO.delete(found);
    }
    public String uploadPhoto1(MultipartFile file, UUID productId) throws IOException {
        Product found = this.findById(productId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setPhoto1(url);
        productDAO.save(found);
        return url;
    }
    public String uploadPhoto2(MultipartFile file, UUID productId) throws IOException {
        Product found = this.findById(productId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setPhoto2(url);
        productDAO.save(found);
        return url;
    }
    public String uploadPhoto3(MultipartFile file, UUID productId) throws IOException {
        Product found = this.findById(productId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setPhoto3(url);
        productDAO.save(found);
        return url;
    }
}
