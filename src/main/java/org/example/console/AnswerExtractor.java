package org.example.console;

import java.util.ArrayList;
import java.util.List;

public class AnswerExtractor {

    public  static List<String> extractAnswers(String input) {
        List<String> answers = new ArrayList<>();
        int currentIndex = 0;
        while (currentIndex < input.length()) {
            int startQuote = input.indexOf("\"", currentIndex);
            int endQuote = input.indexOf("\"", startQuote + 1);

            if (startQuote == -1 || endQuote == -1) {
                break;
            }

            String answer = input.substring(startQuote + 1, endQuote).trim();
            if (!answer.isEmpty() && answer.length() <= 255) {
                answers.add(answer);
            }

            currentIndex = endQuote + 1;
        }

        return answers;
    }
}
