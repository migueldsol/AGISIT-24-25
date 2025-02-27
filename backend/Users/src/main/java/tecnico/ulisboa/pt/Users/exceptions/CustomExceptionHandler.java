package tecnico.ulisboa.pt.Users.exceptions;

import org.apache.catalina.connector.ClientAbortException;
import org.hibernate.exception.LockAcquisitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tecnico.ulisboa.pt.Users.exceptions.ErrorMessage;


// https://www.toptal.com/java/spring-boot-rest-api-error-handling

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger myLogger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserExceptionDto tutorException(UserException e) {
        return new UserExceptionDto(e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public UserExceptionDto accessDeniedException(AccessDeniedException e) {
        myLogger.error(e.getMessage());
        return new UserExceptionDto(ErrorMessage.ACCESS_DENIED);
    }

    @ExceptionHandler(LockAcquisitionException.class)
    @ResponseStatus(HttpStatus.OK)
    public UserExceptionDto lockAcquisitionException(LockAcquisitionException e) {
        myLogger.error("LockAcquisitionException");
        return new UserExceptionDto(e);
    }

    @ExceptionHandler(ClientAbortException.class)
    @ResponseStatus(HttpStatus.OK)
    public void clientAbortException(ClientAbortException e) {
        // Ignore my broken pipe. It still works
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UserExceptionDto randomException(Exception e) {
        myLogger.error(e.getMessage(), e);
        return new UserExceptionDto(e);
    }
}