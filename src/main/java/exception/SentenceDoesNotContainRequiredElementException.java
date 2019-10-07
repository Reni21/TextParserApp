package exception;

public class SentenceDoesNotContainRequiredElementException extends Exception {

    public SentenceDoesNotContainRequiredElementException() {
    }

    public SentenceDoesNotContainRequiredElementException(String message) {
        super(message);
    }
}
