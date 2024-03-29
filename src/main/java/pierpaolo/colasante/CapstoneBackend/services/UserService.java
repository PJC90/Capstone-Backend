package pierpaolo.colasante.CapstoneBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pierpaolo.colasante.CapstoneBackend.entities.User;
import pierpaolo.colasante.CapstoneBackend.entities.enums.Roles;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.payloads.entitiesDTO.UserDTO;
import pierpaolo.colasante.CapstoneBackend.repositories.UserDAO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    Cloudinary cloudinary;

    public Page<User> findAll(int size, int page, String order){
        Pageable pageable = PageRequest.of(size, page, Sort.by(order));
        return userDAO.findAll(pageable);
    }
    public User findById(UUID userId){
        return userDAO.findById(userId).orElseThrow(()->new NotFoundException(userId));
    }
    public User userUpdate(UUID userId, UserDTO body){
        User update = this.findById(userId);
        if(body.name() != null){update.setName(body.name());}
        if(body.surname() != null){update.setSurname(body.surname());}
        if(body.birthday() != null){update.setBirthday(body.birthday());}
//        qui non posso modificare la password entrerebbe in un loop
        if(body.username() != null){update.setUsername(body.username());}
        if(body.email() != null){update.setEmail(body.email());}
        return userDAO.save(update);
    }
    public void userDelete(UUID userId){
        User userDelete = this.findById(userId);
        userDAO.delete(userDelete);
    }
    public User findByEmail(String email){
        return userDAO.findByEmail(email).orElseThrow(()->new NotFoundException("Utente con email " + email + " non trovato..."));
    }

    public String uploadImage(MultipartFile file, UUID userId) throws IOException {
        User found = this.findById(userId);
        String url = (String)cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        userDAO.save(found);
        return url;
    }
    public List<User> getSeller(){
        return userDAO.findByRole(Roles.SELLER);
    }
    public User becomeSeller(UUID userId){
        User seller = findById(userId);
        seller.setRole(Roles.SELLER);
        return userDAO.save(seller);
    }
}
