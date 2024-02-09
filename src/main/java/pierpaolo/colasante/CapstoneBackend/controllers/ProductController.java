package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.Product;
import pierpaolo.colasante.CapstoneBackend.exceptions.BadRequestException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ProductDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ProductResponseDTO;
import pierpaolo.colasante.CapstoneBackend.services.ProductService;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public Page<Product> productList(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "dateCreation") String order){
        return productService.findAllProduct(page, size, order);
    }
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product findProduct(@PathVariable UUID productId){
        return productService.findById(productId);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO saveProduct(@RequestBody @Validated ProductDTO payload, BindingResult validation){
       if(validation.hasErrors()){
           throw new BadRequestException(validation.getAllErrors());
       }else{
           Product product = productService.saveProduct(payload);
           return new ProductResponseDTO(product.getProductId());
       }
    }
    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@PathVariable UUID productId, @RequestBody @Validated ProductDTO payload, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }else{
            return productService.updateProduct(productId, payload);
        }
    }
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('SELLER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID productId){
         productService.deleteProduct(productId);
    }
    @PatchMapping("/{productId}/upload1")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhoto1(@RequestParam("photo1")MultipartFile file, @PathVariable UUID productId) throws Exception{
        return productService.uploadPhoto1(file, productId);
    }
    @PatchMapping("/{productId}/upload2")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhoto2(@RequestParam("photo2")MultipartFile file, @PathVariable UUID productId) throws Exception{
        return productService.uploadPhoto2(file, productId);
    }
    @PatchMapping("/{productId}/upload3")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadPhoto3(@RequestParam("photo3")MultipartFile file, @PathVariable UUID productId) throws Exception{
        return productService.uploadPhoto3(file, productId);
    }
}
