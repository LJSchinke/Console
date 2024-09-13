package org.example.console;

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

        int separatorIndex = input.indexOf("?");
        if (separatorIndex == -1) {
            System.out.println("Invalid format. Make sure the question ends with a '?'.");
            return;
        }

        String question = input.substring(0, separatorIndex+1).trim();
        if (question.length() > 255) {
            System.out.println("Question exceeds the 255 character limit.");
            return;
        }

        String answersPart = input.substring(separatorIndex + 1).trim();
        if (answersPart.isEmpty()) {
            System.out.println("You need to provide at least one answer.");
            return;
        }

        List<String> newAnswers = AnswerExtractor.extractAnswers(answersPart);
        if (newAnswers.isEmpty()) {
            System.out.println("Invalid format. Answers should be enclosed in quotes.");
            return;
        }

        if (QASystem.containsQuestion(question)) {
            System.out.println("This question already exists in the system.");
            System.out.println("Would you like to add new unique answers to this question? (yes/no)");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("yes")) {
                List<String> existingAnswers = QASystem.getAnswersToQuestion(question);
                int addedAnswersCount = 0;

                for (String answer : newAnswers) {
                    if (!existingAnswers.contains(answer)) {
                        existingAnswers.add(answer);
                        addedAnswersCount++;
                    }
                }

                if (addedAnswersCount > 0) {
                    System.out.println(addedAnswersCount + " new unique answer(s) added to the question.");
                } else {
                    System.out.println("All provided answers are already associated with this question.");
                }
            } else {
                System.out.println("No answers were added.");
            }
        } else {
            QASystem.addQuestionAndAnswer(question, newAnswers);
            System.out.println("Question and answers added successfully!");
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