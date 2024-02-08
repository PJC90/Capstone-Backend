package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Category;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoryDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.CategoryDAO;

@Service
public class CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;

    public Category salvaCategoria(CategoryDTO body){
        Category newCategory = new Category();
        newCategory.setNameCategory(body.nameCategory());
        return categoryDAO.save(newCategory);
    }
}
