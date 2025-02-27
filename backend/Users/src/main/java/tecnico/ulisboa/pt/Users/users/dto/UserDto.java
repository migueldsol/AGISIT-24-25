package tecnico.ulisboa.pt.Users.users.dto;

import tecnico.ulisboa.pt.Users.users.domain.User;
import lombok.Getter;
import lombok.Setter;

public class UserDto {
    
    @Getter
    @Setter
    public Integer id;

    @Getter
    @Setter
    public String username;

    @Getter
    @Setter
    public String password;

    @Getter
    @Setter
    public String email;
    
    @Getter
    @Setter
    public String phoneNumber;

    @Getter
    @Setter
    public String address;

    @Getter
    @Setter
    public String city;

    @Getter
    @Setter
    public String country;

    @Getter
    @Setter
    public String postalCode;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.postalCode = user.getPostalCode();
    }

}
