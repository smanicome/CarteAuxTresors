package exceptions;

import java.util.Objects;

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
