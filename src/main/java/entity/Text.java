package entity;

import java.util.List;
import java.util.stream.Collectors;

public class Text {
    private List<Paragraph> paragraphs;

    public Text(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    @Override
    public String toString() {
        return paragraphs.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\n"));
    }
}
