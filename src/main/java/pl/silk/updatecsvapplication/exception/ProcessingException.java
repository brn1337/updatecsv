package pl.silk.updatecsvapplication.exception;

import lombok.Getter;

@Getter
public class ProcessingException extends RuntimeException{

    private final String code;

    public ProcessingException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
    }

    public ProcessingException(String message) {
        super(message);
        this.code = null;
    }
}
