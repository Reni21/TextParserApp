package service;

import entity.Sentence;
import entity.Symbol;
import entity.Token;
import entity.Word;
import exception.SentenceDoesNotContainRequiredElementException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class SentenceServiceImpl implements SentenceService{
    public Sentence deleteSequenceBetweenChars(Sentence sentence, char fromChar, char toChar) throws SentenceDoesNotContainRequiredElementException {
        if (sentence == null) {
            throw new IllegalArgumentException("Source sentence is null.");
        }
        checkRequiredCharsExist(sentence, fromChar, toChar);
        List<Token> tokens = sentence.getTokens();

        List<Token> left = collectTokensLeft(tokens, String.valueOf(fromChar));
        List<Token> right = collectTokensRight(tokens, String.valueOf(toChar));
        left.addAll(right);
        return new Sentence(left);
    }

    private void checkRequiredCharsExist(Sentence sentence, char fromChar, char toChar) throws SentenceDoesNotContainRequiredElementException {
        List<Token> tokens = sentence.getTokens();
        boolean fromExist = tokens.stream()
                .map(Token::getValue)
                .anyMatch(value -> value.contains(String.valueOf(fromChar)));
        boolean toExist = tokens.stream()
                .map(Token::getValue)
                .anyMatch(value -> value.contains(String.valueOf(toChar)));

        if (!fromExist && !toExist) {
            throw new SentenceDoesNotContainRequiredElementException(
                    String.format("Sentence does not contain required chars: %s, %s.", fromChar, toChar)
            );
        }
        if (!fromExist || !toExist) {
            char absent = fromExist ? toChar : fromChar;
            throw new SentenceDoesNotContainRequiredElementException(
                    String.format("Sentence does not contain required char: %s.", absent)
            );
        }
    }

    private List<Token> collectTokensLeft(List<Token> allTokens, String terminalValue) {
        List<Token> result = new ArrayList<>();
        for (Token token : allTokens) {
            String tokenValue = token.getValue();

            if (tokenValue.contains(terminalValue)) {
                if (tokenValue.startsWith(terminalValue)) {
                    return result;
                } else {
                    String newValue = tokenValue.substring(0, tokenValue.indexOf(terminalValue));
                    result.add(createNewTokenConsideringSubtype(token, newValue));
                    return result;
                }
            } else {
                result.add(createNewTokenConsideringSubtype(token, tokenValue));
            }
        }
        return result;
    }

    private Token createNewTokenConsideringSubtype(Token token, String value) {
        if (token instanceof Word) {
            return new Word(value);
        }
        return new Symbol(value);
    }

    private List<Token> collectTokensRight(List<Token> allTokens, String terminalValue) {
        List<Token> result = new ArrayList<>();
        ListIterator<Token> iterator = allTokens.listIterator(allTokens.size());
        while (iterator.hasPrevious()) {
            Token token = iterator.previous();
            String tokenValue = token.getValue();

            if (tokenValue.contains(terminalValue)) {
                if (tokenValue.endsWith(terminalValue)) {
                    return result;
                } else {
                    String newValue = tokenValue.substring(tokenValue.lastIndexOf(terminalValue) + 1);
                    result.add(0, createNewTokenConsideringSubtype(token, newValue));
                    return result;
                }
            } else {
                result.add(0, createNewTokenConsideringSubtype(token, tokenValue));
            }
        }
        return result;
    }

    public Sentence cloneSentence(Sentence sentence) {
        if(sentence == null){
            throw new IllegalArgumentException("Source sentence is null.");
        }
        List<Token> cloneTokens = sentence.getTokens().stream()
                .map(token -> createNewTokenConsideringSubtype(token, token.getValue()))
                .collect(Collectors.toList());
        return new Sentence(cloneTokens);
    }
}
