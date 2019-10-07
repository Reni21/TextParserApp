import entity.Text;
import lombok.AllArgsConstructor;
import service.SentenceService;
import service.TextService;
import util.TextParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TextParserApp {
    private static String INTRO = "\033[93mHello! It is the app for text modification.\n" +
            "You can cut some part from every sentences. Choose two symbols which will be used for it (inclusive).\n" +
            "\"--show\"     show source text\n" +
            "\"--q\"        command for quite the app\033[0m";

    private TextService textService;

    public static void main(String[] args) {
        TextService textService = new TextService(new SentenceService());
        TextParserApp app = new TextParserApp(textService);

        try (Scanner scanner = new Scanner(System.in)) {
            Text text = app.generateText();
            app.runTextParserApp(text, scanner);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void runTextParserApp(Text text, Scanner scanner) {
        System.out.println(text + "\n\n");
        System.out.println(INTRO);

        while (true) {
            try {
                requestAndProcessInput(scanner, text);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void requestAndProcessInput(Scanner scanner, Text text) {
        System.out.println("\nEnter char to start cut from:");
        String leftTerminal = scanner.nextLine();

        String rightTerminal = null;
        if (handleInput(leftTerminal, text)) {
            System.out.println("Enter char to start cut before:");
            rightTerminal = scanner.nextLine();
        }
        if (rightTerminal != null && handleInput(rightTerminal, text)) {
            Text transformedText = textService.deleteSubstringInEverySentence(text, leftTerminal.charAt(0), rightTerminal.charAt(0));
            System.out.println(transformedText);
        }
    }

    private boolean handleInput(String input, Text text) {
        switch (input) {
            case "--q":
                System.out.println("You just quite the app...");
                System.exit(0);
                break;
            case "--show":
                System.out.println(text);
                return false;
            default:
                validateInput(input);
        }
        return true;
    }

    private void validateInput(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Please, enter a char");
        }
        if (input.length() > 1) {
            throw new IllegalArgumentException("Use one char, not a char sequence");
        }
    }

    public Text generateText() throws IOException {
        TextParser textParser = new TextParser();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("text.txt");

        String textSrc;
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
            textSrc = buffer.lines().collect(Collectors.joining("\n"));
        }
        return textParser.parseText(textSrc);
    }
}
