package tecnico.ulisboa.pt.Users.exceptions;

public enum ErrorMessage {
    INVALID_NAME("Invalid name"),
    INVALID_EMAIL("Invalid email"),
    INVALID_PHONE_NUMBER("Invalid phone number"),
    INVALID_PASSWORD("Invalid password"),
    INVALID_ADDRESS("Invalid address"),
    INVALID_CITY("Invalid city"),
    INVALID_COUNTRY("Invalid country"),
    INVALID_POSTAL_CODE("Invalid postal code"),
    USER_NOT_FOUND("User with username %s not found"),
    INVALID_LOGIN_CREDENTIALS("Invalid login credentials"),
    INVALID_AUTH_USER("Invalid auth user"),
    USER_ALREADY_EXISTS("User with username %s already exists"),
    USERNAME_ALREADY_EXISTS("User with name %s already exists"),
    EMAIL_ALREADY_EXISTS("User with email %s already exists"),
    INVALID_CONFIRMATION_TOKEN("Invalid confirmation token"),
    EXPIRED_CONFIRMATION_TOKEN("Confirmation token is expired"),
    ACCESS_DENIED("You do not have permission to view this resource"),
    ;

    public final String label;

    ErrorMessage(String label) {
        this.label = label;
    }
}
