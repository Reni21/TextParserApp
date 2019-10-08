package util;

import entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextParser {
    private static final String CUT_SENTENCE_REGEX = "(?<=(?<![A-Z])\\. )";
    private static final String CUT_PARAGRAPHS_REGEX = "\\n+";
    private static final String CUT_TOKENS_REGEX = "\\s*\\s|[.]{3}|\\p{Punct}|[\\S&&\\P{Punct}]+";

    public Text parseText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Source text-string is null.");
        }
        List<Paragraph> paragraphs = parseParagraphs(text);
        return new Text(paragraphs);
    }

    private List<Paragraph> parseParagraphs(String text) {
        String[] paragraphs = text.split(CUT_PARAGRAPHS_REGEX);
        return Arrays.stream(paragraphs)
                .map(this::parseSentences)
                .map(Paragraph::new)
                .collect(Collectors.toList());
    }

    private List<Sentence> parseSentences(String paragraph) {
        String[] sentences = paragraph.split(CUT_SENTENCE_REGEX);
        return Arrays.stream(sentences)
                .map(String::trim)
                .map(this::parseTokens)
                .map(Sentence::new)
                .collect(Collectors.toList());
    }

    private List<Token> parseTokens(String sentence) {
        List<String> splitElements = new ArrayList<>();
        Pattern pattern = Pattern.compile(CUT_TOKENS_REGEX);
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            splitElements.add(matcher.group());
        }
        return splitElements.stream()
                .map(element -> Character.isLetterOrDigit(element.charAt(0)) ?
                        new Word(element) :
                        new Symbol(element))
                .collect(Collectors.toList());
    }
}
