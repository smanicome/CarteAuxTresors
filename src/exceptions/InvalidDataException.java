package exceptions;

import java.util.Objects;

/**
 * Exception to be thrown when the data in the input file is deemed invalid
 */
public class InvalidDataException extends Throwable {
    private final String message;

    public InvalidDataException(String message) {
        this.message = Objects.requireNonNull(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
