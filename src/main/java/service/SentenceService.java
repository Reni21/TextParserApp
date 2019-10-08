package service;

import entity.Sentence;
import exception.SentenceDoesNotContainRequiredElementException;

public interface SentenceService {
    Sentence deleteSequenceBetweenChars(Sentence sentence, char fromChar, char toChar) throws SentenceDoesNotContainRequiredElementException;

    Sentence cloneSentence(Sentence sentence);
}
