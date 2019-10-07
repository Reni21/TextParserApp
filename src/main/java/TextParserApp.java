import entity.Text;
import service.SentenceService;
import service.TextService;
import util.TextParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TextParserApp {
    private static String INTRO = "\033[93mHello! It is the app for text modification.\n" +
            "You can cut some part from every sentences. Choose two symbols which will be used for it (inclusive).\n" +
            "Use \"--q\" command for quite the app\033[0m";

    private TextService textService;

    public TextParserApp(TextService textService) {
        this.textService = textService;
    }

    public static void main(String[] args) {
        SentenceService sentenceService = new SentenceService();
        TextService textService = new TextService(sentenceService);
        TextParserApp app = new TextParserApp(textService);

        try (Scanner scanner = new Scanner(System.in)) {
            Text text = app.generateText();
            app.runTextParserApp(text, scanner);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void runTextParserApp(Text text, Scanner scanner) {
        System.out.println(text + "\n\n");
        System.out.println(INTRO);

        while (true) {
            try {
                System.out.println("\nEnter char to start cut from:");
                String left = scanner.nextLine();
                validateInput(left);
                System.out.println("Enter char to start cut before:");
                String right = scanner.nextLine();
                validateInput(right);

                Text transformedText = textService.cutSubstringInEverySentence(text, left.charAt(0), right.charAt(0));
                System.out.println(transformedText);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void validateInput(String input) {
        if (input.equals("--q")) {
            System.out.println("You just quite the app...");
            System.exit(0);
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Please, enter a char");
        }
        if (input.length() > 1) {
            throw new IllegalArgumentException("Use one char, not a char sequence");
        }
    }

    private Text generateText() throws IOException, URISyntaxException{
        TextParser textParser = new TextParser();
        String textStr = Files.lines(Paths.get(ClassLoader.getSystemResource("text.txt").toURI()))
                .collect(Collectors.joining("\n"));
        return textParser.parseText(textStr);
    }
}
