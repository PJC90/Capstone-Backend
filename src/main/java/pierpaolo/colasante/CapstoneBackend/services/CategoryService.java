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
import pierpaolo.colasante.CapstoneBackend.entities.Category;
import pierpaolo.colasante.CapstoneBackend.entities.Product;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoryDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.CategoryDAO;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private Cloudinary cloudinary;
    public Page<Category> findAllCategory(int page, int size, String order){
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        return categoryDAO.findAll(pageable);
    }
    public Category salvaCategoria(CategoryDTO body){
        Category newCategory = new Category();
        newCategory.setNameCategory(body.nameCategory());
        newCategory.setPhotoCategory("https://ui-avatars.com/api/?name=" + body.nameCategory());
        return categoryDAO.save(newCategory);
    }
    public Category findById(int categoriaId){
        return categoryDAO.findById(categoriaId).orElseThrow(()-> new NotFoundException(categoriaId));}
    public void deleteCategoria(int categoriaId){
        Category found = this.findById(categoriaId);
        categoryDAO.delete(found);
    }
    public Category modificaCategoria(int categoriaId, CategoryDTO body){
        Category found = this.findById(categoriaId);
        found.setNameCategory(body.nameCategory());
        return categoryDAO.save(found);
    }
    public String uploadPhotoCategory(MultipartFile file, int categoryId)throws IOException{
        Category found = this.findById(categoryId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setPhotoCategory(url);
        categoryDAO.save(found);
        return url;
    }
    public List<Product> getProductByCategory(int categoriaId){
        Category foud = this.findById(categoriaId);
        return foud.getProduct();
    }
}
