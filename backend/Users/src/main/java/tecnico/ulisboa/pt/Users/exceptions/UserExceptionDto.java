package tecnico.ulisboa.pt.Users.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

interface UserExceptionSubError extends Serializable {
}

public class UserExceptionDto implements UserExceptionSubError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp;

    private String message;

    private String debugMessage;

    private List<UserExceptionSubError> subErrors;


    UserExceptionDto(Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public UserExceptionDto(UserException e) {
        this.timestamp = LocalDateTime.now();
        this.message = e.getMessage();
    }

    public UserExceptionDto(ErrorMessage errorMessage) {
        this.timestamp = LocalDateTime.now();
        this.message = errorMessage.label;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public List<UserExceptionSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<UserExceptionSubError> subErrors) {
        this.subErrors = subErrors;
    }
}