package org.example.console;

import org.example.console.exceptions.InvalidAnswerFormatException;
import org.example.console.exceptions.InvalidQuestionFormatException;
import org.example.console.exceptions.InvalidQuestionLengthException;
import org.example.console.exceptions.MissingAnswerException;

import java.util.*;

public class Main {

    public static QuestionAnswerSystem QASystem = new QuestionAnswerSystem();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1 Ask a question");
            System.out.println("2 Add a question and its answers");
            System.out.println("3 List of questions");
            System.out.println("4 Reset Questions");
            System.out.println("5 Exit");
            System.out.println("? Help");

            String option = scanner.nextLine();

            switch (option) {
                case "1" -> askQuestion(scanner);
                case "2" -> addQuestion(scanner);
                case "3" -> provideQuestions();
                case "4" -> resetQuestions(scanner);
                case "5" -> {
                    System.out.println("Exiting...");
                    return;
                }
                case "?" -> printHelp();
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void resetQuestions(Scanner scanner) {

        System.out.println("This will reset the system to contain no questions.");
        System.out.println("Would you like to reset the system? (yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            QASystem.resetSystem();
            System.out.println("The System was reset.");
        } else {
            System.out.println("The System was NOT reset.");
        }
    }

    private static void askQuestion(Scanner scanner) {
        System.out.println("Enter your question:");
        String question = scanner.nextLine();

        if (QASystem.containsQuestion(question)) {
            List<String> answers = QASystem.getAnswersToQuestion(question);
            System.out.println("Answers:");
            for (String answer : answers) {
                System.out.println(answer);
            }
        } else {
            System.out.println("the answer to life, universe and everything is 42");
        }
    }

    private static void addQuestion(Scanner scanner) {
        System.out.println("Enter your question followed by answers in the format:");
        System.out.println("<Question>? \"<First answer>\"(necessary) \"<Second answer>\"(optional) \"<nth answer>\"(optional)");

        String input = scanner.nextLine();

        try{
            Map<String, List<String>> processedInput = QASystem.processInputToQuestionAndAnswer(input);
            String question = processedInput.keySet().toArray()[0].toString();
            if (QASystem.containsQuestion(question)) {
                addNewAnswersToQuestion(scanner, question, processedInput.get(question));
            } else {
                QASystem.addQuestionAndAnswer(question, processedInput.get(question));
                System.out.println("Question and answers added successfully!");
            }

        }
        catch (InvalidAnswerFormatException
               | MissingAnswerException
               | InvalidQuestionLengthException
               | InvalidQuestionFormatException e
        ) {
            System.out.println(e.getMessage());
        }
    }

    private static void addNewAnswersToQuestion(Scanner scanner, String question, List<String> newAnswers) {
        System.out.println("This question already exists in the system.");
        System.out.println("Would you like to add new unique answers to this question? (yes/no)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            int addedAnswers = QASystem.addNewAnswersToQuestion(question, newAnswers);
            if (addedAnswers > 0) {
                System.out.println(addedAnswers + " new unique answer(s) added to the question.");
            } else {
                System.out.println("All provided answers are already associated with this question.");
            }
        } else {
            System.out.println("No answers were added.");
        }
    }

    private static void provideQuestions() {

        Set<String> questions = QASystem.getQuestions();
        if (questions.isEmpty()) {
            System.out.println("No questions stored yet.");
            return;
        }

        System.out.println("Stored questions:");
        for (String question : questions) {
            System.out.println(question);
        }
    }

    private static void printHelp() {

        System.out.println("The QuestionAnswerConsole is used to get answers to get answers to a provided question");
        System.out.println("You can either ask a question, add a question or provide yourself with the list of stored questions.");
        System.out.println("Asking a question: if the question is in the system you will get the stored answer," +
                "\n if not you will get a standard answer." +
                "\n The question has to be an exact match, there is no \"best match\"");
        System.out.println("Adding a question:" +
                "\n if the question is in the system you will get the option to add the unique answers or to discard them," +
                "\n if not the question will be added to stored list." +
                "\n You will have to provide the question in given syntax: <Question>? \"<First answer>\"(necessary) \"<nth answer>\"(optional)");
        System.out.println("List of stored questions:" +
                "\n this will provide you with a list of stored questions.\n");
        System.out.println("Reset Questions:" +
                "\n this will reset the list of stored questions.\n" +
                "Meaning there will be no questions left in the system");
    }
}