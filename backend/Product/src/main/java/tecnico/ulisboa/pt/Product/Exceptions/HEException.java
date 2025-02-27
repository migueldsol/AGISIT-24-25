package tecnico.ulisboa.pt.Product.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HEException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(HEException.class);

    private final ErrorMessage errorMessage;

    public HEException(ErrorMessage errorMessage) {
        super(errorMessage.message);
        logger.error(errorMessage.message);
        this.errorMessage = errorMessage;
    }

    public HEException(ErrorMessage errorMessage, Integer value) {
        super(String.format(errorMessage.message, value));
        logger.error(String.format(errorMessage.message, value));
        this.errorMessage = errorMessage;
    }

    public HEException(ErrorMessage errorMessage, Integer value, Integer value2) {
        super(String.format(errorMessage.message, value, value2));
        logger.error(String.format(errorMessage.message, value, value2));
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
