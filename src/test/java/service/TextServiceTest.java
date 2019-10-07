package service;

import entity.*;
import exception.SentenceDoesNotContainRequiredElementException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static testutil.DataProvider.*;

@RunWith(MockitoJUnitRunner.class)
public class TextServiceTest {
    @Mock
    private SentenceService sentenceService;
    @InjectMocks
    private TextService instance;

    @Before
    public void setUp() {
        instance = new TextService(sentenceService);
    }

    @Test
    public void shouldReturnNotNullTextValueAfterDelete() throws SentenceDoesNotContainRequiredElementException {
        Text srcText = getTestText();
        List<Paragraph> srcParagraphs = srcText.getParagraphs();
        Sentence sentenceOne = srcParagraphs.get(0).getSentences().get(0);
        Sentence sentenceTwo = srcParagraphs.get(1).getSentences().get(0);

        when(sentenceService.deleteSequenceBetweenChars(sentenceOne, 't', 'n'))
                .thenReturn(getReturnSentenceOne());
        when(sentenceService.deleteSequenceBetweenChars(sentenceTwo, 't', 'n'))
                .thenReturn(getReturnSentenceTwo());

        Text res = instance.deleteSubstringInEverySentence(srcText, 't', 'n');
        assertNotNull(res);
    }

    @Test
    public void shouldReturnTextWithEmptyParagraphsIfAfterDeleteReturnsEmptySentences() throws SentenceDoesNotContainRequiredElementException {
        Text srcText = getTestText();
        List<Paragraph> srcParagraphs = srcText.getParagraphs();
        Sentence sentenceOne = srcParagraphs.get(0).getSentences().get(0);
        Sentence sentenceTwo = srcParagraphs.get(1).getSentences().get(0);
        when(sentenceService.deleteSequenceBetweenChars(sentenceOne, 'M', '!'))
                .thenReturn(new Sentence(new ArrayList<Token>()));
        when(sentenceService.deleteSequenceBetweenChars(sentenceTwo, 'M', '!'))
                .thenReturn(new Sentence(new ArrayList<Token>()));

        Text resText = instance.deleteSubstringInEverySentence(srcText, 'M', '!');
        List<Paragraph> res = resText.getParagraphs();
        assertTrue(res.isEmpty());
    }

    @Test
    public void shouldReturnTextWithNumberOfSentencesInEveryParagraphsEqualsOne() throws SentenceDoesNotContainRequiredElementException {
        Text srcText = getTestText();
        List<Paragraph> srcParagraphs = srcText.getParagraphs();
        Sentence sentenceOne = srcParagraphs.get(0).getSentences().get(0);
        Sentence sentenceTwo = srcParagraphs.get(1).getSentences().get(0);

        when(sentenceService.deleteSequenceBetweenChars(sentenceOne, 't', 'n'))
                .thenReturn(getReturnSentenceOne());
        when(sentenceService.deleteSequenceBetweenChars(sentenceTwo, 't', 'n'))
                .thenReturn(getReturnSentenceTwo());

        Text res = instance.deleteSubstringInEverySentence(srcText, 't', 'n');
        List<Paragraph> paragraphs = res.getParagraphs();
        paragraphs.stream()
        .map(Paragraph::getSentences)
                .forEach(sentences -> assertEquals(1, sentences.size()));
    }

    @Test
    public void shouldReturnTextWithNumberParagraphsEqualsTwo() throws SentenceDoesNotContainRequiredElementException {
        Text srcText = getTestText();
        List<Paragraph> srcParagraphs = srcText.getParagraphs();
        Sentence sentenceOne = srcParagraphs.get(0).getSentences().get(0);
        Sentence sentenceTwo = srcParagraphs.get(1).getSentences().get(0);

        when(sentenceService.deleteSequenceBetweenChars(sentenceOne, 't', 'n'))
                .thenReturn(getReturnSentenceOne());
        when(sentenceService.deleteSequenceBetweenChars(sentenceTwo, 't', 'n'))
                .thenReturn(getReturnSentenceTwo());

        Text resText = instance.deleteSubstringInEverySentence(srcText, 't', 'n');
        int res = resText.getParagraphs().size();
        assertEquals(2,res);
    }

    @Test
    public void shouldReturnNewText() throws SentenceDoesNotContainRequiredElementException {
        Text srcText = getTestText();
        List<Paragraph> srcParagraphs = srcText.getParagraphs();
        Sentence sentenceOne = srcParagraphs.get(0).getSentences().get(0);
        Sentence sentenceTwo = srcParagraphs.get(1).getSentences().get(0);

        when(sentenceService.deleteSequenceBetweenChars(sentenceOne, 't', 'n'))
                .thenReturn(getReturnSentenceOne());
        when(sentenceService.deleteSequenceBetweenChars(sentenceTwo, 't', 'n'))
                .thenReturn(getReturnSentenceTwo());

        Text res = instance.deleteSubstringInEverySentence(srcText, 't', 'n');
        assertNotSame(srcText, res);
    }

    @Test
    public void shouldReturnTextWithTheSameOrderOfParagraphsAsInSrc() throws SentenceDoesNotContainRequiredElementException {
        Text srcText = getTestText();
        List<Paragraph> srcParagraphs = srcText.getParagraphs();
        Sentence sentenceOne = srcParagraphs.get(0).getSentences().get(0);
        Sentence sentenceTwo = srcParagraphs.get(1).getSentences().get(0);
        when(sentenceService.deleteSequenceBetweenChars(sentenceOne, '%', '+'))
                .thenThrow(SentenceDoesNotContainRequiredElementException.class);
        when(sentenceService.cloneSentence(sentenceOne)).thenReturn(getTestSentenceOne());
        when(sentenceService.deleteSequenceBetweenChars(sentenceTwo, '%', '+'))
                .thenThrow(SentenceDoesNotContainRequiredElementException.class);
        when(sentenceService.cloneSentence(sentenceTwo)).thenReturn(getTestSentenceTwo());

        Text resText = instance.deleteSubstringInEverySentence(srcText, '%', '+');
        List<Paragraph> resParagraphs = resText.getParagraphs();
        int size = srcParagraphs.size();
        for (int i = 0; i < size; i++) {
            assertEquals(srcParagraphs.get(i).getValue(), resParagraphs.get(i).getValue());
        }
    }

    @Test
    public void shouldReturnNewTextWithEveryNewParagraphs() throws SentenceDoesNotContainRequiredElementException {
        Text srcText = getTestText();
        List<Paragraph> srcParagraphs = srcText.getParagraphs();
        Sentence sentenceOne = srcParagraphs.get(0).getSentences().get(0);
        Sentence sentenceTwo = srcParagraphs.get(1).getSentences().get(0);
        when(sentenceService.deleteSequenceBetweenChars(sentenceOne, '%', '+'))
                .thenThrow(SentenceDoesNotContainRequiredElementException.class);
        when(sentenceService.cloneSentence(sentenceOne)).thenReturn(getTestSentenceOne());
        when(sentenceService.deleteSequenceBetweenChars(sentenceTwo, '%', '+'))
                .thenThrow(SentenceDoesNotContainRequiredElementException.class);
        when(sentenceService.cloneSentence(sentenceTwo)).thenReturn(getTestSentenceTwo());

        Text resText = instance.deleteSubstringInEverySentence(srcText, '%', '+');
        List<Paragraph> resParagraphs = resText.getParagraphs();
        int size = srcParagraphs.size();
        for (int i = 0; i < size; i++) {
            assertNotSame(srcParagraphs.get(i), resParagraphs.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenSourceTextIsNull(){
        instance.deleteSubstringInEverySentence(null, '%', '+');
    }
}
