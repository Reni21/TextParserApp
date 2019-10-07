package exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SentenceDoesNotContainRequiredElementException extends Exception {

    public SentenceDoesNotContainRequiredElementException(String message) {
        super(message);
    }
}
