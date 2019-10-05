package service;

import entity.*;

import java.util.ArrayList;
import java.util.List;

public class TextParserService {
    public Text parse(String src) {


        return null;
    }

    private List<Sentence> parseSentence(String sentence) {
        return null;
    }


    private List<Token> parseToken(String token) {
        List<Token> tokens = new ArrayList<>();

        if (token.contains("\\W") || token.contains("_")) {
            String start = token.substring(0, 1);
            String end = token.substring(token.length() - 1);

            if (start.equals("_") || start.equals("\\W")) {
                new Symbol(start);
                token = token.substring(1);
            }

            List<Symbol> ends = new ArrayList<>();
            while (end.equals("_") || end.equals("\\W")) {
                token = token.substring(0, token.length() - 1);
                ends.add(0, new Symbol(end));
                end = token.substring(token.length() - 1);
            }
            tokens.add(new Word(token));
            tokens.addAll(ends);
            tokens.add(new Symbol(" "));
        } else {
            tokens.add(new Word(token));
            tokens.add(new Symbol(" "));
        }
        return tokens;
    }
}
