package util;

import entity.Paragraph;
import entity.Sentence;
import entity.Text;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TextParserTest {
    private TextParser textParser;

    @Before
    public void setUp() {
        textParser = new TextParser();
    }

    @Test
    public void shouldReturnNotNullText() {
        Text res = textParser.parseText("Test text.");
        assertNotNull(res);
    }

    @Test
    public void shouldReturnTextWithTwoParagraphsAfterParsing() {
        Text resText = textParser.parseText("Test text one.\nTest text two.");
        List<Paragraph> paragraphs = resText.getParagraphs();
        int res = paragraphs.size();
        assertEquals(2, res);
    }

    @Test
    public void shouldContainOneSentenceInEachParagraphAfterParsing() {
        Text resText = textParser.parseText("Test text one.\nTest text two.");
        List<Paragraph> resParagraphs = resText.getParagraphs();
        resParagraphs.stream()
                .map(Paragraph::getSentences)
                .forEach(sentences -> assertEquals(1, sentences.size()));
    }

    @Test
    public void shouldContainSixTokensInEachSentenceAfterParsing() {
        Text resText = textParser.parseText("Test text one.\nTest text two.");
        List<Paragraph> resParagraphs = resText.getParagraphs();
        resParagraphs.stream()
                .flatMap(paragraph -> paragraph.getSentences().stream())
                .map(Sentence::getTokens)
                .forEach(tokens -> assertEquals(6, tokens.size()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfSourceStringForParsingIsNull(){
        textParser.parseText(null);
    }
}