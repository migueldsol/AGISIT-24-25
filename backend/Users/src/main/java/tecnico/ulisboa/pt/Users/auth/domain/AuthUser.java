package tecnico.ulisboa.pt.Users.auth.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;

import jakarta.persistence.*;
import lombok.*;
import tecnico.ulisboa.pt.Users.exceptions.ErrorMessage;
import tecnico.ulisboa.pt.Users.exceptions.UserException;
import tecnico.ulisboa.pt.Users.users.domain.User;

@Entity
@Table(name = "auth_users")
public class AuthUser implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Getter
    @Setter
    @Column(unique = true)
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String confirmationToken = "";

    @Getter
    @Setter
    private LocalDateTime tokenGenerationDate;

    @OneToOne
    @Getter
    @Setter
    private User user;

    public AuthUser() {
    }

    public AuthUser(User user, String username) {
        this.username = username;
        this.user = user;

        generateConfirmationToken();
        
        verifyInvariants();
    }

    public String generateConfirmationToken() {
        String token = KeyGenerators.string().generateKey();
        setTokenGenerationDate(LocalDateTime.now());
        setConfirmationToken(token);
        return token;
    }

    public void checkConfirmationToken(String token) {
 
        if (!token.equals(getConfirmationToken()))
            throw new UserException(ErrorMessage.INVALID_CONFIRMATION_TOKEN);
        if (getTokenGenerationDate().isBefore(LocalDateTime.now().minusDays(1)))
            throw new UserException(ErrorMessage.EXPIRED_CONFIRMATION_TOKEN);
    }

    public void verifyInvariants() {

        if (username == null || username.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_NAME);
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
