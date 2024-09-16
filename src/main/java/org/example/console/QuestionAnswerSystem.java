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

        List<String> answers = AnswerExtractor.extractAnswers(answersPart);
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
}
