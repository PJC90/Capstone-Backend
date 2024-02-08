package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.Category;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoryDTO;
import pierpaolo.colasante.CapstoneBackend.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/cat")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public List<Category> trovaCategorie(){
        return categoryService.findAllCategory();
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category salvaCategoria(@RequestBody CategoryDTO body){
        return categoryService.salvaCategoria(body);
    }
    @PutMapping("/{categoriaId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category modificaCategoria(@PathVariable int categoriaId, @RequestBody CategoryDTO body){
        return categoryService.modificaCategoria(categoriaId, body);
    }

    @DeleteMapping("/{categoriaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoria(@PathVariable int categoriaId){
        categoryService.deleteCategoria(categoriaId);
    }
}
