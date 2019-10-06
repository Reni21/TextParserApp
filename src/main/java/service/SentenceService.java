package service;

import entity.Sentence;
import entity.Token;
import exception.SentenceNotContainRequiredElementException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SentenceService {
    public Sentence deleteSequenceBetweenChars(Sentence sentence, char fromChar, char toChar) throws SentenceNotContainRequiredElementException {
        if (sentence == null) {
            throw new IllegalArgumentException();
        }
        checkRequiredCharsExist(sentence, fromChar, toChar);
        List<Token> tokens = sentence.getTokens();

        List<Token> left = collectTokensLeft(tokens, String.valueOf(fromChar));
        List<Token> right = collectTokensRight(tokens, String.valueOf(toChar));
        left.addAll(right);
        return new Sentence(left);
    }

    private void checkRequiredCharsExist(Sentence sentence, char fromChar, char toChar) throws SentenceNotContainRequiredElementException {
        List<Token> tokens = sentence.getTokens();
        boolean fromExist = tokens.stream()
                .map(Token::getValue)
                .anyMatch(value -> value.contains(String.valueOf(fromChar)));
        boolean toExist = tokens.stream()
                .map(Token::getValue)
                .anyMatch(value -> value.contains(String.valueOf(toChar)));;

        if (!fromExist && !toExist) {
            throw new SentenceNotContainRequiredElementException(
                    String.format("Sentence does not contain required chars: %s , %s.", fromChar, toChar)
            );
        }
        if (!fromExist || !toExist) {
            char absent = fromExist ? toChar : fromChar;
            throw new SentenceNotContainRequiredElementException(
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
                    token.setValue(newValue);
                    result.add(token);
                    return result;
                }
            } else {
                result.add(token);
            }
        }
        return result;
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
                    String newValue = tokenValue.substring(tokenValue.indexOf(terminalValue) + 1);
                    token.setValue(newValue);
                    result.add(0, token);
                    return result;
                }
            } else {
                result.add(0, token);
            }
        }
        return result;
    }
}
