package service;

import entity.Sentence;
import entity.Token;
import exception.SentenceDoesNotContainRequiredElementException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;
import static testutil.DataProvider.*;

public class SentenceServiceTest {
    private SentenceService instance;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        instance = new SentenceService();
    }

    // deleteSequenceBetweenChars(Sentence sentence, char fromChar, char toChar) method tests

    @Test
    public void shouldDeleteSequenceBetweenRequiredCharsInclusiveBothChars() throws SentenceDoesNotContainRequiredElementException {
        Sentence afterDelete = instance.deleteSequenceBetweenChars(getTestSentenceOne(), 't', 'n');
        String res = afterDelete.getValue();
        String expected = "My e!";
        assertEquals(expected, res);
    }

    @Test
    public void shouldReturnNotNullIfRequiredCharsIncludeAllSentence() throws SentenceDoesNotContainRequiredElementException {
        Sentence res = instance.deleteSequenceBetweenChars(getTestSentenceOne(), 'M', '!');
        assertNotNull(res);
    }

    @Test
    public void shouldReturnSentenceWithEmptyTokensIfRequiredCharsIncludeAllSentence() throws SentenceDoesNotContainRequiredElementException {
        Sentence afterDelete = instance.deleteSequenceBetweenChars(getTestSentenceOne(), 'M', '!');
        List<Token> res = afterDelete.getTokens();
        assertTrue(res.isEmpty());
    }

    @Test
    public void shouldReturnNewSentenceAsResult() throws SentenceDoesNotContainRequiredElementException {
        Sentence src = getTestSentenceOne();
        Sentence res = instance.deleteSequenceBetweenChars(src, 't', 'n');
        assertNotSame(src, res);
    }

    @Test
    public void shouldNotModifySrcTokens() throws SentenceDoesNotContainRequiredElementException {
        Sentence src = getTestSentenceOne();
        String srcBeforeDelete = src.getValue();
        instance.deleteSequenceBetweenChars(src, 't', 'n');
        String srcAfterDelete = src.getValue();
        assertEquals(srcBeforeDelete, srcAfterDelete);
    }

    @Test
    public void shouldThrowSentenceDoesNotContainRequiredCharsExceptionIfBothCharsAreNotExist() throws SentenceDoesNotContainRequiredElementException {
        expectedException.expect(SentenceDoesNotContainRequiredElementException.class);
        expectedException.expectMessage("Sentence does not contain required chars: %, +.");
        instance.deleteSequenceBetweenChars(getTestSentenceOne(), '%', '+');
    }

    @Test
    public void shouldThrowSentenceDoesNotContainRequiredCharsExceptionIfOneOfCharsAreNotExists() throws SentenceDoesNotContainRequiredElementException {
        expectedException.expect(SentenceDoesNotContainRequiredElementException.class);
        expectedException.expectMessage("Sentence does not contain required char: +.");
        instance.deleteSequenceBetweenChars(getTestSentenceOne(), 't', '+');
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfSourceSentenceForCutIsNull() throws SentenceDoesNotContainRequiredElementException {
        instance.deleteSequenceBetweenChars(null, 't', 'a');
    }

    // cloneSentence(Sentence sentence) method tests

    @Test
    public void shouldReturnNotNullAfterClone() {
        Sentence res = instance.cloneSentence(getTestSentenceOne());
        assertNotNull(res);
    }

    @Test
    public void shouldReturnNewSentenceAfterClone() {
        Sentence src = getTestSentenceOne();
        Sentence res = instance.cloneSentence(src);
        assertNotSame(src, res);
    }

    @Test
    public void shouldReturnSentenceWithTheSameValueAsSrc() {
        Sentence src = getTestSentenceOne();
        String scrValue = src.getValue();
        Sentence res = instance.cloneSentence(src);
        String resValue = res.getValue();
        assertEquals(scrValue, resValue);
    }

    @Test
    public void shouldReturnSentenceWithTokensInTheSameOrderAsInSourceSentence() {
        Sentence srcSentence = getTestSentenceOne();
        List<Token> srcTokens = srcSentence.getTokens();
        Sentence resSentence = instance.cloneSentence(srcSentence);
        List<Token> resTokens = resSentence.getTokens();
        int size = srcTokens.size();
        for (int i = 0; i < size; i++) {
            assertEquals(srcTokens.get(i), resTokens.get(i));
        }
    }

    @Test
    public void shouldReturnSentenceWithEveryNewTokenAfterClone() {
        Sentence srcSentence = getTestSentenceOne();
        List<Token> srcTokens = srcSentence.getTokens();
        Sentence resSentence = instance.cloneSentence(srcSentence);
        List<Token> resTokens = resSentence.getTokens();
        int size = srcTokens.size();
        for (int i = 0; i < size; i++) {
            assertNotSame(srcTokens.get(i), resTokens.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfSourceSentenceForCloneIsNull() {
        instance.cloneSentence(null);
    }

}
