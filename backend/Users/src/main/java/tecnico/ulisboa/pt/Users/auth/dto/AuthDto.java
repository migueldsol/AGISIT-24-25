package tecnico.ulisboa.pt.Users.auth.dto;

import lombok.*;

public class AuthDto {

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private AuthUserDto user;

    public AuthDto() {
    }

    public AuthDto(String token, AuthUserDto user) {
        this.token = token;
        this.user = user;
    }
}
