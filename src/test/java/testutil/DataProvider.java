package testutil;

import entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProvider {
    public static Text getTestText() {
        List<Sentence> listSentenceOne = new ArrayList<>(1);
        listSentenceOne.add(getTestSentenceOne());
        List<Sentence> listSentenceTwo = new ArrayList<>(1);
        listSentenceTwo.add(getTestSentenceTwo());

        Paragraph paragraphOne = new Paragraph(listSentenceOne);
        Paragraph paragraphTwo = new Paragraph(listSentenceTwo);

        List<Paragraph> paragraphs = new ArrayList<>();
        paragraphs.add(paragraphOne);
        paragraphs.add(paragraphTwo);
        return new Text(paragraphs);
    }

    public static Sentence getTestSentenceOne() {
        Token[] value = {
                new Word("My"),
                new Symbol(" "),
                new Word("test"),
                new Symbol(" "),
                new Word("sentence"),
                new Symbol(" "),
                new Word("one"),
                new Symbol("!")
        };
        return new Sentence(Arrays.asList(value));
    }
    public static Sentence getTestSentenceTwo() {
        Token[] value = {
                new Word("My"),
                new Symbol(" "),
                new Word("test"),
                new Symbol(" "),
                new Word("sentence"),
                new Symbol(" "),
                new Word("two"),
                new Symbol("!")
        };
        return new Sentence(Arrays.asList(value));
    }

    public static Sentence getReturnSentenceOne() {
        Token[] value = {
                new Word("My"),
                new Symbol(" "),
                new Word("e"),
                new Symbol("!")
        };
        return new Sentence(Arrays.asList(value));
    }
    public static Sentence getReturnSentenceTwo() {
        Token[] value = {
                new Word("My"),
                new Symbol(" "),
                new Word("ce"),
                new Symbol(" "),
                new Word("two"),
                new Symbol("!")
        };
        return new Sentence(Arrays.asList(value));
    }
}
