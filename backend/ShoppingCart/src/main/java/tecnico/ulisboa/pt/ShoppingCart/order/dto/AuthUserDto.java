package tecnico.ulisboa.pt.ShoppingCart.order.dto;


public class AuthUserDto {

    private String username;


    public AuthUserDto() {
    }

    public AuthUserDto(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

}
