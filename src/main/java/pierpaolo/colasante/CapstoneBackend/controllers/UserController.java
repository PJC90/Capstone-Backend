package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.Shop;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ShopDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.ShopResponseDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.UserDTO;
import pierpaolo.colasante.CapstoneBackend.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> userslist(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "userId") String order){
        return userService.findAll(page, size, order);
    }
    @GetMapping("/seller")
    public List<User> getSeller(){
        return userService.getSeller();
    }
    @PutMapping("/seller")
    public User becomeSeller(@AuthenticationPrincipal User user){
        return userService.becomeSeller(user.getUserId());
    }
    @GetMapping("/me")
    public User profilePage(@AuthenticationPrincipal User user) {return user;}
    @PutMapping("/me")
    public User updateUser(@AuthenticationPrincipal User user, @RequestBody UserDTO body){
        return userService.userUpdate(user.getUserId(), body);
    }
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal User user){
        userService.userDelete(user.getUserId());
    }
    @PatchMapping("/me/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@RequestParam("image")MultipartFile file, @AuthenticationPrincipal User user) throws Exception {
        return userService.uploadImage(file, user.getUserId());
    }
}
