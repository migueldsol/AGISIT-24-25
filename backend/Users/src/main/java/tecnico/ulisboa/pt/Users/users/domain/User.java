package tecnico.ulisboa.pt.Users.users.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tecnico.ulisboa.pt.Users.auth.domain.AuthUser;
import tecnico.ulisboa.pt.Users.exceptions.ErrorMessage;
import tecnico.ulisboa.pt.Users.exceptions.UserException;
import tecnico.ulisboa.pt.Users.users.dto.UserDto;

@Entity
@Table(name = "users")
public class User {
    
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
    @Column(unique = true)
    private String email;
    
    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String postalCode;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private AuthUser authUser;

    public User() {
    }

    public User(String username, String email, String phoneNumber, String address, String city, String country, String postalCode) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.authUser = new AuthUser(this, username);

        this.authUser.setUser(this);

        verifyInvariants();
    }

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.phoneNumber = userDto.getPhoneNumber();
        this.address = userDto.getAddress();
        this.city = userDto.getCity();
        this.country = userDto.getCountry();
        this.postalCode = userDto.getPostalCode();
        this.authUser = new AuthUser(this, userDto.getUsername());
        this.authUser.setUser(this);

        verifyInvariants();
    }

    public void verifyInvariants() {
        if (username == null || username.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_NAME);
        }

        if (email == null || email.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_EMAIL);
        }

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_PHONE_NUMBER);
        }

        if (address == null || address.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_ADDRESS);
        }

        if (city == null || city.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_CITY);
        }

        if (country == null || country.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_COUNTRY);
        }

        if (postalCode == null || postalCode.isEmpty()) {
            throw new UserException(ErrorMessage.INVALID_POSTAL_CODE);
        }

        if (authUser == null) {
            throw new UserException(ErrorMessage.INVALID_AUTH_USER);
        }
    }

    public void setPassword(String password) {
        if (authUser == null) {
            throw new UserException(ErrorMessage.INVALID_AUTH_USER);
        }

        authUser.setPassword(password);
    }

}
