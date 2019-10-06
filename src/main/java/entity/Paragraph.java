package entity;

import java.util.List;
import java.util.stream.Collectors;

public class Paragraph {
    private List<Sentence> sentences;

    public Paragraph(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        return sentences.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
