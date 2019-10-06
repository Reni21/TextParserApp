import entity.Sentence;
import entity.Text;
import exception.SentenceNotContainRequiredElementException;
import service.SentenceService;
import service.TextParserService;

public class Main {
    public static void main(String[] args) {
        String text = "Encapsulation — Object-oriented programming (OOP) is a programming 0.0 language model in which programs are organized around data, or objects, rather than functions and logic. An object can be defined as a data field that has unique attributes and behavior. Examples of an object can range from physical entities, such as a human being that is described by properties like name and address, down to small computer programs, such as widgets. This opposes the historical approach to programming where emphasis was placed on how the logic was written rather than how to define the data within the logic.\n" +
                "The first step in OOP is to identify all of the objects a programmer wants to manipulate and how they relate to each other, an exercise often known as data modeling. Once an object is known, it is generalized as a class of objects that defines the kind of data it contains and any logic sequences that can manipulate it. Each distinct logic sequence is known as a method and objects can communicate with well-defined interfaces called messages.\n" +
                "Simply put, OOP focuses on the objects that developers want to manipulate rather than the logic required to manipulate them. This approach to programming is well-suited for programs that are large, complex and actively updated or maintained. Due to the organization of an object-oriented program, this method is also conducive to collaborative development where projects can be divided into groups. Additional benefits of OOP include code reusability, scalability  and efficiency.\n" +
                "Principles of OOP\n" +
                "Object-oriented programming is based on the following principles:\n" +
                "Encapsulation — The implementation and state of each object are privately held inside a defined boundary, or class. Other objects do not have access to this class or the authority to make changes but are only able to call a list of public functions, or methods. This characteristic of data hiding provides greater program security and avoids unintended data corruption.\n" +
                "Abstraction — Objects only reveal internal mechanisms that are relevant for the use of other objects, hiding any unnecessary implementation code. This concept helps developers make changes and additions over time more easily.\n" +
                "Inheritance — Relationships and subclasses between objects can be assigned, allowing developers to reuse a common logic while still maintaining a unique hierarchy. This property of OOP forces a more thorough data analysis, reduces development time and ensures a higher level of accuracy.\n" +
                "Polymorphism — Objects are allowed to take on more than one form depending on the context. The program will determine which meaning or usage is necessary for each execution of that object, cutting down on the need to duplicate code.";

        // token parser
        String test = "Object-oriented programming (OOP) is a programming 0.0 language model in which programs are organized around data, or objects, rather than functions and logic.";
        //String test = "a";
        TextParserService parserService = new TextParserService();
        Text t = parserService.parseText(test);

        Sentence sentence = t.getParagraphs().get(0).getSentences().get(0);
        //System.out.println("sent = " + sentence);
        SentenceService sentenceService = new SentenceService();

        try {
            Sentence newS = sentenceService.deleteSequenceBetweenChars(sentence, '-', ' ');
            System.out.println(sentence);
            System.out.println(newS);
        } catch (SentenceNotContainRequiredElementException e) {
            System.out.println(e.getMessage());
        }

    }
}
