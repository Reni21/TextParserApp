package entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Sentence {
    private List<Token> tokens;

    public void addToken(Token token) {
        if (tokens != null) {
            tokens.add(token);
        }
    }

    public String getValue(){
        return tokens.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
