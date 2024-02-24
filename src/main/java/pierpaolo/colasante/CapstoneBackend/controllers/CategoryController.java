package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.Category;
import pierpaolo.colasante.CapstoneBackend.entities.Product;
import pierpaolo.colasante.CapstoneBackend.exceptions.BadRequestException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoryDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.CategoryResponseDTO;
import pierpaolo.colasante.CapstoneBackend.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public Page<Category> trovaCategorie(@RequestParam(defaultValue = "0")int page,
                                         @RequestParam(defaultValue = "100") int size,
                                         @RequestParam(defaultValue = "nameCategory") String order){
        return categoryService.findAllCategory(page, size, order);
    }
    @GetMapping("/{categoriaId}")
    public List<Product> prodottiByCategoria(@PathVariable int categoriaId){
        return categoryService.getProductByCategory(categoriaId);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO salvaCategoria(@RequestBody @Validated CategoryDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else {
            Category category = categoryService.salvaCategoria(body);
            return new CategoryResponseDTO(category.getCategoryId());
        }
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

    @PatchMapping("/{categoriaId}/photocategory")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhotoCategory(@RequestParam("photo") MultipartFile file, @PathVariable int categoriaId) throws Exception{
        return categoryService.uploadPhotoCategory(file, categoriaId);
    }
}
