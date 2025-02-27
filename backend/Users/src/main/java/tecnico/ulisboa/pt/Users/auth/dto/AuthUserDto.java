package tecnico.ulisboa.pt.Users.auth.dto;

import lombok.Getter;
import lombok.Setter;
import tecnico.ulisboa.pt.Users.auth.domain.AuthUser;

public class AuthUserDto {
    
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String username;


    public AuthUserDto() {
    }

    public AuthUserDto(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public AuthUserDto(AuthUser authUser) {
        this.id = authUser.getId();
        this.username = authUser.getUsername();
    }
}
