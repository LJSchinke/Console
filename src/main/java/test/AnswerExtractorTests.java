package test;

import org.junit.Test;

import java.util.List;

import static org.example.console.AnswerExtractor.extractAnswers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnswerExtractorTests {

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
