package pierpaolo.colasante.CapstoneBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pierpaolo.colasante.CapstoneBackend.entities.enums.Roles;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({ "authorities", "accountNonExpired", "enabled",
        "accountNonLocked", "credentialsNonExpired", "reviewBuyerList",
        "shopList", "cart", "orderList", "userBuyerDetail", "userSellerDetail"})
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID userId;
    private String name;
    private String surname;
    private String avatar;
    private LocalDate birthday;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;
    @OneToMany(mappedBy = "seller")
    private List<Shop> shopList;
    @OneToMany(mappedBy = "buyerReview")
    private List<Review> reviewBuyerList;
    @OneToMany(mappedBy = "userId")
    private List<Order> orderList;
    @OneToOne(mappedBy = "user")
    private Cart cart;
    @OneToOne(mappedBy = "buyerDetail")
    private UserBuyerDetail userBuyerDetail;
    @OneToOne(mappedBy = "sellerDetail")
    private UserSellerDetail userSellerDetail;

    public void becomeSeller(){
        this.role = Roles.SELLER;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}
}
