package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.services.UserService;

import java.io.IOException;
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
    @PreAuthorize("hasAuthority('SELLER')")
    public List<User> getSeller(){
        return userService.getSeller();
    }
    @GetMapping("/me")
    public User profilePage(@AuthenticationPrincipal User user) {return user;}
    @PutMapping("/me")
    public User updateUser(@AuthenticationPrincipal User user, @RequestBody User body){
        return userService.userUpdate(user.getUserId(), body);
    }
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal User user){
        userService.userDelete(user.getUserId());
    }
    @PatchMapping("/{userId}/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImageUser(@RequestParam("image")MultipartFile file, @PathVariable UUID userId) throws Exception {
        return userService.uploadImage(file, userId);
    }
}
