package exception;

public class SentenceDoesNotContainRequiredElementException extends Exception {

    public SentenceDoesNotContainRequiredElementException(String message) {
        super(message);
    }
}
