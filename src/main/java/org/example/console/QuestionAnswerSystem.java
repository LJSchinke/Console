package org.example.console;

import org.example.console.exceptions.*;

import java.util.*;

public class QuestionAnswerSystem {

    private HashMap<String, List<String>> QuestionAnswerMap;

    public QuestionAnswerSystem(Map<String, List<String>> initializeWith){
        if (initializeWith==null){
            QuestionAnswerMap=new HashMap<>();
        }else{
            QuestionAnswerMap=new HashMap<>(initializeWith);
        }
    }

    public boolean containsQuestion(String question){

        return QuestionAnswerMap.containsKey(question);
    }

    public List<String> getAnswersToQuestion(String question){

        return QuestionAnswerMap.get(question);
    }

    public Map<String, List<String>> processInputToQuestionAndAnswer(String input)
            throws
            InvalidAnswerFormatException,
            MissingAnswerException,
            InvalidQuestionLengthException,
            InvalidQuestionFormatException,
            InvalidAnswerLengthException {
        int separatorIndex = input.indexOf("?");
        if (separatorIndex==-1){
            throw new InvalidQuestionFormatException();
        }

        String question = input.substring(0, separatorIndex+1).trim();
        if (question.length() > 255) {
            throw new InvalidQuestionLengthException();
        }

        String answersPart = input.substring(separatorIndex + 1).trim();
        if (answersPart.isEmpty()) {
            throw new MissingAnswerException();
        }

        List<String> answers = extractAnswers(answersPart);
        if (answers.isEmpty()) {
            throw new InvalidAnswerFormatException();
        }

        for(String answer : answers){
            if (answer.length() > 255){
                throw new InvalidAnswerLengthException();
            }
        }

        return Map.of(question, answers);
    }

    public void addQuestionAndAnswer(String question, List<String> answers) {

        QuestionAnswerMap.put(question, answers);
    }

    public int addNewAnswersToQuestion(String question, List<String> newAnswers){

        ArrayList<String> existingAnswers = new ArrayList<>();
        getAnswersToQuestion(question).forEach(answer -> existingAnswers.add(answer));
        int addedAnswersCount = 0;

        for (String answer : newAnswers) {
            if (!existingAnswers.contains(answer)) {
                existingAnswers.add(answer);
                addedAnswersCount++;
            }
        }
        if (addedAnswersCount>0){
            addQuestionAndAnswer(question,existingAnswers);
            return addedAnswersCount;
        }else {
            return 0;
        }
    }

    public Set<String> getQuestions (){
        return QuestionAnswerMap.keySet();
    }

    public void resetSystem(){
        QuestionAnswerMap=new HashMap<String, List<String>>();
    }

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
