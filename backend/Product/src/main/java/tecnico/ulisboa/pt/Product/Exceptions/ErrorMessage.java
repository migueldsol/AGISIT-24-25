package tecnico.ulisboa.pt.Product.Exceptions;

public enum ErrorMessage {
    PRODUCT_NOT_FOUND("Product not found with id %d"),
    PRODUCT_QUANTITY_INSUFFICIENT("Product with id %d has insufficient quantity to reduce by %d"),
    INVALID_LOGIN_CREDENTIALS("Invalid login credentials"),
    ;

    public final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
