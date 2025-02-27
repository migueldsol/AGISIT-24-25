package tecnico.ulisboa.pt.Product.Dto;

import lombok.Getter;
import lombok.Setter;

public class AuthUserDto {

    @Getter
    @Setter
    private String username;


    public AuthUserDto() {
    }

    public AuthUserDto(String username) {
        this.username = username;
    }

}
