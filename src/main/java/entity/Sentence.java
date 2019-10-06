package entity;

import java.util.List;
import java.util.stream.Collectors;

public class Sentence {
    private List<Token> tokens;

    public Sentence(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public int getSize() {
        return tokens.size();
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void addToken(Token token) {
        if (tokens != null) {
            tokens.add(token);
        }
    }

    @Override
    public String toString() {
        return tokens.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
