package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> userslist(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "userId") String order){
        return userService.findAll(page, size, order);
    }
    @GetMapping("/me")
    public User profilePage(@AuthenticationPrincipal User user) {return user;}
}
