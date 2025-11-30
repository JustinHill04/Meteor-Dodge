// Must have at least one class that creates a custom exception
public class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}