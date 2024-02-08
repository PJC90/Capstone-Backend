package pierpaolo.colasante.CapstoneBackend.controllers;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.Categories;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoriesDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoriesResponseDTO;
import pierpaolo.colasante.CapstoneBackend.services.CategoriesService;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;
    @GetMapping
    public Page<Categories> reviewsList(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "shopId") String order){
        return categoriesService.findAllCategories(page, size, order);
    }
    @PostMapping
    public Categories saveCategory(@RequestBody CategoriesDTO payload){
        System.out.println(payload.nameplate());
        System.out.println("ciao");
        return categoriesService.saveCategor(payload);
    }
    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Categories updateCategory(@PathVariable int categoryId, @RequestBody CategoriesDTO payload){
        return categoriesService.updateCategory(categoryId, payload);
    }
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCategory(@PathVariable int categoryId){
        categoriesService.deleteCategory(categoryId);
    }
}
