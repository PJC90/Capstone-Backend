package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.entities.enums.Roles;
import pierpaolo.colasante.CapstoneBackend.exceptions.BadRequestException;
import pierpaolo.colasante.CapstoneBackend.exceptions.UnauthorizedException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.UserDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.loginDTO.UserLoginDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.UserDAO;
import pierpaolo.colasante.CapstoneBackend.security.JWTtools;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JWTtools jwTtools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUser(UserLoginDTO body){
        User found = userService.findByEmail(body.email());
        if(passwordEncoder.matches(body.password(), found.getPassword())) {
            return jwTtools.createToken(found);
        }else{
            throw new UnauthorizedException("Credenziali non valide...");
        }
    }
    public User save(UserDTO body){
        userDAO.findByEmail(body.email()).ifPresent(user -> {throw new BadRequestException("email " + user.getEmail() + " già in uso...");});
        userDAO.findByUsername(body.username()).ifPresent(user -> {throw new BadRequestException("Username " + user.getUsername() + " già in uso...");});
        User newUser = new User();
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setBirthday(body.birthday());
        newUser.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname() + "&background=E38F38&color=fff");
        newUser.setRole(Roles.USER);
        return userDAO.save(newUser);
    }
}
