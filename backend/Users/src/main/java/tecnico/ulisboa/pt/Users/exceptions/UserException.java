package tecnico.ulisboa.pt.Users.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;



@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(UserException.class);

    private final ErrorMessage errorMessage;

    public UserException(ErrorMessage errorMessage) {
        super(errorMessage.label);
        logger.error(errorMessage.label);
        this.errorMessage = errorMessage;
    }

    public UserException(ErrorMessage errorMessage, String value){
        super(String.format(errorMessage.label, value));
        logger.error(String.format(errorMessage.label, value));
        this.errorMessage = errorMessage;
    }

    public UserException(ErrorMessage errorMessage, String value, String value2){
        super(String.format(errorMessage.label, value, value2));
        logger.error(String.format(errorMessage.label, value, value2));
        this.errorMessage = errorMessage;
    }

    public UserException(ErrorMessage errorMessage, int value){
        super(String.format(errorMessage.label, value));
        logger.error(String.format(errorMessage.label, value));
        this.errorMessage = errorMessage;
    }

    public UserException(ErrorMessage errorMessage, int value, int value2){
        super(String.format(errorMessage.label, value, value2));
        logger.error(String.format(errorMessage.label, value, value2));
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}