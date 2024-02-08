package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Categories;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoriesDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.CategoriesDAO;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesDAO categoriesDAO;
    public Page<Categories> findAllCategories(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return categoriesDAO.findAll(pageable);
    }
    public Categories findById(int categoryId){
        return categoriesDAO.findById(categoryId).
                orElseThrow(()->new NotFoundException(categoryId));}
    public Categories saveCategor(CategoriesDTO body){
        System.out.println("*****sto stampando: " + body.nameplate());
        Categories newCat = new Categories();
        newCat.setNameplate(body.nameplate());
        System.out.println("*****sto stampando: " + body.nameplate());
        return categoriesDAO.save(newCat);
    }
    public Categories updateCategory(int categoryId, CategoriesDTO body){
        Categories updateCategory = this.findById(categoryId);
        updateCategory.setNameplate(body.nameplate());
        return categoriesDAO.save(updateCategory);
    }
    public void deleteCategory(int categoryId){
        Categories deleteCategory = this.findById(categoryId);
        categoriesDAO.delete(deleteCategory);
    }
}
