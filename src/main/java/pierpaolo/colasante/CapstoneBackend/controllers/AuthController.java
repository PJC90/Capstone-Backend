package pierpaolo.colasante.CapstoneBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.exceptions.BadRequestException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.UserDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.UserResponseDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.loginDTO.UserLoginDTO;
import pierpaolo.colasante.CapstoneBackend.payloads.loginDTO.UserLoginResponseDTO;
import pierpaolo.colasante.CapstoneBackend.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public UserLoginResponseDTO login (@RequestBody UserLoginDTO body){
        String accessToken = authService.authenticateUser(body);
        return new UserLoginResponseDTO(accessToken);
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Validated UserDTO newUserPayload, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.hasErrors());
            throw new BadRequestException(validation.getAllErrors());
        } else{
            User newUser = authService.save(newUserPayload);
            return new UserResponseDTO(newUser.getUserId());
        }
    }
}
