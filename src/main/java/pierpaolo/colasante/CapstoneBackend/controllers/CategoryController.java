package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pierpaolo.colasante.CapstoneBackend.entities.Category;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoryDTO;
import pierpaolo.colasante.CapstoneBackend.services.CategoryService;

@RestController
@RequestMapping("/cat")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public Category salvaCategoria(@RequestBody CategoryDTO body){
        return categoryService.salvaCategoria(body);
    }
}
