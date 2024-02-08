package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Category;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoryDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.CategoryDAO;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;
    public List<Category> findAllCategory(){
        return categoryDAO.findAll();
    }
    public Category salvaCategoria(CategoryDTO body){
        Category newCategory = new Category();
        newCategory.setNameCategory(body.nameCategory());
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
}
