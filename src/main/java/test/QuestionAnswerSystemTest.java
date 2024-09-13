package test;

import java.util.*;

import org.example.console.QuestionAnswerSystem;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.example.console.AnswerExtractor.extractAnswers;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionAnswerSystemTest {

    public static QuestionAnswerSystem QASystem = new QuestionAnswerSystem();;

    @Test
    public void testContainsQuestion_ContainsQuestion() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer1 = "answer1";
        String answer2 = "answer2";

        QASystem.QuestionAnswerMap = new HashMap<>(Map.of(questionToBeInSystem, List.of(answer1, answer2)));

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
        QASystem.QuestionAnswerMap = new HashMap<>(Map.of(questionToBeInSystem, List.of(answer1, answer2)));
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
        QASystem.QuestionAnswerMap = new HashMap<>(Map.of(questionToBeInSystem, List.of(answer)));

        // Act
        List<String> result = QASystem.getAnswersToQuestion(questionToBeInSystem);

        // Assert
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), answer);
    }

    @Test
    public void getAnswersToQuestion_MultipleAnswers() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer1 = "answer1";
        String answer2 = "answer2";
        QASystem.QuestionAnswerMap = new HashMap<>(Map.of(questionToBeInSystem, List.of(answer1, answer2)));

        // Act
        List<String> result = QASystem.getAnswersToQuestion(questionToBeInSystem);

        // Assert
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), answer1);
        assertEquals(result.get(1), answer2);
    }

    @Test
    public void getAnswersToQuestion_QuestionNotInSystem() {
        // Arrange
        String questionToBeInSystem ="Test?";
        String answer1 = "answer1";
        String answer2 = "answer2";
        QASystem.QuestionAnswerMap = new HashMap<>(Map.of(questionToBeInSystem, List.of(answer1, answer2)));
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
        QASystem.QuestionAnswerMap = new HashMap<>(Map.of(question0, List.of(answer0, answer1)));

        // Act
        Set<String> result = QASystem.getQuestions();

        // Assert
        assertEquals(result.size(),1);
        assertTrue(result.contains(question0));
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
        QASystem.QuestionAnswerMap = new HashMap<>(
                Map.of(question0, List.of(answer0_0, answer0_1),
                        question1, List.of(answer1_0, answer1_1)
                )
        );

        // Act
        Set<String> result = QASystem.getQuestions();

        // Assert
        assertEquals(result.size(),2);
        assertTrue(result.contains(question0));
        assertTrue(result.contains(question1));
    }

    @Test
    public void getQuestions_NoQuestionsInSystem() {
        // Arrange
        QASystem.QuestionAnswerMap = new HashMap<>();

        // Act
        Set<String> result = QASystem.getQuestions();

        // Assert
        assert(result.isEmpty());
    }
}
