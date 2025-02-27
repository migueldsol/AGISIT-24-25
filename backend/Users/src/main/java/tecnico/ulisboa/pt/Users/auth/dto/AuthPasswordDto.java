package tecnico.ulisboa.pt.Users.auth.dto;

import lombok.*;

public class AuthPasswordDto {

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String username;

    public AuthPasswordDto() {
    }

    public AuthPasswordDto(String password, String username) {
        this.password = password;
        this.username = username;
    }
}
