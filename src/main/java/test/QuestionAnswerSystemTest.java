package test;

import java.util.*;

import org.example.console.QuestionAnswerSystem;
import org.example.console.exceptions.*;
import org.junit.Test;

import static org.example.console.QuestionAnswerSystem.extractAnswers;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionAnswerSystemTest {

    public static QuestionAnswerSystem QASystem = new QuestionAnswerSystem(null);;

    @Test
    public void testContainsQuestion_ContainsQuestion() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer1 = "answer1";
        String answer2 = "answer2";

        QASystem = new QuestionAnswerSystem(Map.of(questionToBeInSystem, List.of(answer1, answer2)));

        // Act
        boolean result = QASystem.containsQuestion(questionToBeInSystem);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testContainsQuestion_DoesNotContainQuestion() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer1 = "answer1";
        String answer2 = "answer2";
        QASystem = new QuestionAnswerSystem(Map.of(questionToBeInSystem, List.of(answer1, answer2)));
        String questionAsked ="hallo??";

        // Act
        boolean result = QASystem.containsQuestion(questionAsked);

        // Assert
        assertFalse(result);
    }

    @Test
    public void getAnswersToQuestion_SingleAnswer() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer = "answer1";
        QASystem = new QuestionAnswerSystem(Map.of(questionToBeInSystem, List.of(answer)));

        // Act
        List<String> result = QASystem.getAnswersToQuestion(questionToBeInSystem);

        // Assert
        assertEquals(1, result.size());
        assertEquals(result.get(0), answer);
    }

    @Test
    public void getAnswersToQuestion_MultipleAnswers() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer1 = "answer1";
        String answer2 = "answer2";
        QASystem = new QuestionAnswerSystem(Map.of(questionToBeInSystem, List.of(answer1, answer2)));

        // Act
        List<String> result = QASystem.getAnswersToQuestion(questionToBeInSystem);

        // Assert
        assertEquals(2, result.size());
        assertEquals(result.get(0), answer1);
        assertEquals(result.get(1), answer2);
    }

    @Test
    public void getAnswersToQuestion_QuestionNotInSystem() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer1 = "answer1";
        String answer2 = "answer2";
        QASystem = new QuestionAnswerSystem(Map.of(questionToBeInSystem, List.of(answer1, answer2)));
        String questionAsked = "OK?";

        // Act
        List<String> result = QASystem.getAnswersToQuestion(questionAsked);

        // Assert
        assertNull(result);
    }

    @Test
    public void getQuestions_SingleQuestionInSystem() {
        // Arrange
        String question0 ="Test?";
        String answer0 = "answer1";
        String answer1 = "answer2";
        QASystem = new QuestionAnswerSystem(Map.of(question0, List.of(answer0, answer1)));

        // Act
        Set<String> result = QASystem.getQuestions();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(question0));
    }

    @Test
    public void addNewAnswersToQuestion_NoAnswerProvided() {
        // Arrange
        String question0 ="Test?";
        String answer0 = "answer1";
        String answer1 = "answer2";
        QASystem = new QuestionAnswerSystem(Map.of(question0, List.of(answer0, answer1)));

        // Act
        int result = QASystem.addNewAnswersToQuestion(question0,List.of());

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void addNewAnswersToQuestion_NoAnswerAddedAnswerAlreadyMapped() {
        // Arrange
        String question0 ="Test?";
        String answer0 = "answer1";
        String answer1 = "answer2";
        String newAnswer = "answer2";
        QASystem = new QuestionAnswerSystem(Map.of(question0, List.of(answer0, answer1)));

        // Act
        int result = QASystem.addNewAnswersToQuestion(question0,List.of(newAnswer));

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void addNewAnswersToQuestion_SingleAnswerAdded() {
        // Arrange
        String question0 ="Test?";
        String answer0 = "answer1";
        String answer1 = "answer2";
        String newAnswer= "answer3";
        QASystem = new QuestionAnswerSystem(Map.of(question0, List.of(answer0, answer1)));

        // Act
        int result = QASystem.addNewAnswersToQuestion(question0,List.of(newAnswer));

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void getQuestions_MultipleQuestionsInSystem() {
        // Arrange
        String question0 ="Test?";
        String answer0_0 = "answer1";
        String answer0_1 = "answer2";
        String question1 ="tesT?";
        String answer1_0 = "1rewsna";
        String answer1_1 = "2rewsna";
        QASystem = new QuestionAnswerSystem(
                Map.of(question0, List.of(answer0_0, answer0_1),
                        question1, List.of(answer1_0, answer1_1)
                ));

        // Act
        Set<String> result = QASystem.getQuestions();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(question0));
        assertTrue(result.contains(question1));
    }

    @Test
    public void getQuestions_NoQuestionsInSystem() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);

        // Act
        Set<String> result = QASystem.getQuestions();

        // Assert
        assert(result.isEmpty());
    }

    @Test
    public void processInputToQuestionAndAnswer_NoQuestionMark_ThrowsInvalidQuestionFormatException() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);
        String input ="Hallo";

        // Act && Assert
        assertThrows(InvalidQuestionFormatException.class,
                ()-> QASystem.processInputToQuestionAndAnswer(input));
    }

    @Test
    public void processInputToQuestionAndAnswer_QuestionTooLong_ThrowsInvalidQuestionLengthException() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);
        String input ="Dies ist eine zu lange frage weil die frage auch einfach kompliziert formuliert " +
                "ist und an sich auch kontextuell wenig Sinn machen wird in ein System einzuspeichern" +
                " und es nahezu keinen direkt Anwendungsfallgeben wird diese Frage zu " +
                "stellen wenn man ein direktes Match treffen muss?";

        // Act && Assert
        assertThrows(InvalidQuestionLengthException.class,
                ()-> QASystem.processInputToQuestionAndAnswer(input));
    }

    @Test
    public void processInputToQuestionAndAnswer_QuestionValidNoAnswers_ThrowsMissingAnswerException() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);
        String input ="Tolle Frage? ";

        // Act && Assert
        assertThrows(MissingAnswerException.class,
                ()-> QASystem.processInputToQuestionAndAnswer(input));
    }

    @Test
    public void processInputToQuestionAndAnswer_QuestionValidInvalidAnswer_ThrowsInvalidAnswerFormatException() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);
        String input ="Tolle Frage? \"Antwort1";

        // Act && Assert
        assertThrows(InvalidAnswerFormatException.class,
                ()-> QASystem.processInputToQuestionAndAnswer(input));
    }

    @Test
    public void processInputToQuestionAndAnswer_QuestionValidAnswerTooLong_ThrowsInvalidAnswerLengthException() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);
        String input ="Tolle Frage? " +
                "\"Dies ist eine zu lange Antwort weil die Antwort auch " +
                "einfach kompliziert formuliert ist und an sich auch kontextuell wenig Sinn machen wird" +
                " in ein System einzuspeichern und es nahezu keinen direkt Anwendungsfallgeben" +
                " wird diese Antwort zu bekommen wenn man dies auf der Kommandozeile benutzt.\"";

        // Act && Assert
        assertThrows(InvalidAnswerFormatException.class,
                ()-> QASystem.processInputToQuestionAndAnswer(input));
    }

    @Test
    public void processInputToQuestionAndAnswer_ValidQuestionsOneAnswer() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);
        String input ="Tolle Frage? " +
                "\"Super Antwort\"";
        Map<String,List<String>> expected = Map.of("Tolle Frage?",List.of("Super Antwort"));
        // Act
        try{
            Map<String,List<String>> result = QASystem.processInputToQuestionAndAnswer(input);

            // Assert
            assertEquals(expected, result);
        } catch (Exception | InvalidAnswerFormatException | MissingAnswerException | InvalidQuestionLengthException |
                 InvalidQuestionFormatException | InvalidAnswerLengthException e) {

            // Assert
            fail();
        }
    }

    @Test
    public void processInputToQuestionAndAnswer_ValidQuestionsFourAnswer() {
        // Arrange
        QASystem = new QuestionAnswerSystem(null);
        String input ="Tolle Frage? " +
                "\"Super Antwort\" " +
                "\"Weitere Antwort\"" +
                "\"Noch eine\"" +
                "\"Und wieder eine Antwort\"";
        Map<String,List<String>> expected = Map.of("Tolle Frage?",
                List.of("Super Antwort","Weitere Antwort","Noch eine","Und wieder eine Antwort"));
        // Act
        try{
            Map<String,List<String>> result = QASystem.processInputToQuestionAndAnswer(input);

            // Assert
            assertEquals(expected, result);
        } catch (Exception
                 | InvalidAnswerFormatException
                 | MissingAnswerException
                 | InvalidQuestionLengthException
                 | InvalidQuestionFormatException
                 | InvalidAnswerLengthException e)
        {

            // Assert
            fail();
        }
    }

    @Test
    public void testExtractAnswers_MultipleAnswers() {
        // Arrange
        String question = "What is Peters favorite food?";
        List<String> answers = List.of("Pizza","Spaghetti","Ice cream");
        String input = "What is Peters favorite food? \"Pizza\" \"Spaghetti\" \"Ice cream\"";

        // Act
        List<String> result = extractAnswers(input);

        // Assert
        assertEquals(answers.size(), result.size());
        assertTrue(result.contains(answers.get(0)));
        assertTrue(result.contains(answers.get(1)));
        assertTrue(result.contains(answers.get(2)));
    }

    @Test
    public void testExtractAnswer_SingleAnswer() {
        // Arrange
        String question = "What is the capital of France?";
        List<String> answers = List.of("Paris");
        String input = "What is the capital of France? \"Paris\"";

        // Act
        List<String> result = extractAnswers(input);

        // Assert
        assertEquals(answers.size(), result.size());
        assertTrue(result.contains(answers.get(0)));
    }

    @Test
    public void testExtractAnswer_NoAnswers() {
        // Arrange
        String question = "What is the capital of France?";

        // Act
        List<String> result = extractAnswers(question);

        // Assert
        assertEquals(0, result.size());
    }
}
