package service;

import entity.Paragraph;
import entity.Sentence;
import entity.Text;
import exception.SentenceDoesNotContainRequiredElementException;

import java.util.List;
import java.util.stream.Collectors;

public class TextService {
    private SentenceService sentenceService;

    public TextService(SentenceService sentenceService) {
        this.sentenceService = sentenceService;
    }

    public Text deleteSubstringInEverySentence(Text src, char from, char to) {
        if (src == null) {
            throw new IllegalArgumentException("Source text is null.");
        }
        List<Paragraph> transformedParagraphs = src.getParagraphs().stream().map(paragraph -> {
            List<Sentence> transformedSentences = paragraph.getSentences().stream().map(sentence -> {
                try {
                    return sentenceService.deleteSequenceBetweenChars(sentence, from, to);
                } catch (SentenceDoesNotContainRequiredElementException ex) {
                    return sentenceService.cloneSentence(sentence);
                }
            }).filter(sentence -> !sentence.getTokens().isEmpty())
                    .collect(Collectors.toList());
            return new Paragraph(transformedSentences);
        }).filter(paragraph -> !paragraph.getSentences().isEmpty())
                .collect(Collectors.toList());
        return new Text(transformedParagraphs);
    }
}
