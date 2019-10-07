package entity;

import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Paragraph {
    private List<Sentence> sentences;

    public Paragraph(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

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
