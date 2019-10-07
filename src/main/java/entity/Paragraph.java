package entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Paragraph {
    private List<Sentence> sentences;

    public String getValue(){
        return sentences.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return getValue();
    }
}
