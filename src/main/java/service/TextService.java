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

    public Text cutSubstringInEverySentence(Text src, char from, char to) {
        List<Paragraph> transformedParagraphs = src.getParagraphs().stream().map(paragraph -> {
            List<Sentence> transformedSentences = paragraph.getSentences().stream().map(sentence -> {
                try {
                    return sentenceService.deleteSequenceBetweenChars(sentence, from, to);
                } catch (SentenceDoesNotContainRequiredElementException ex) {
                    return sentenceService.cloneSentence(sentence);
                }
            }).collect(Collectors.toList());
            return new Paragraph(transformedSentences);
        }).collect(Collectors.toList());
        return new Text(transformedParagraphs);
    }
}
