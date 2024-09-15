package org.example.console;

import org.example.console.exceptions.InvalidAnswerFormatException;
import org.example.console.exceptions.InvalidQuestionFormatException;
import org.example.console.exceptions.InvalidQuestionLengthException;
import org.example.console.exceptions.MissingAnswerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestionAnswerSystem {

    public HashMap<String, List<String>> QuestionAnswerMap; //AccessModifier changed for UnitTesting

    public QuestionAnswerSystem(){
        QuestionAnswerMap=new HashMap<String, List<String>>();
    }

    public boolean containsQuestion(String question){

        return QuestionAnswerMap.containsKey(question);
    }

    public List<String> getAnswersToQuestion(String question){

        return QuestionAnswerMap.get(question);
    }

    public Map<String, List<String>> processInputToQuestionAndAnswer(String input)throws InvalidAnswerFormatException, MissingAnswerException, InvalidQuestionLengthException, InvalidQuestionFormatException {
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

        return Map.of(question, answers);
    }

    public void addQuestionAndAnswer(String question, List<String> answers) {

        QuestionAnswerMap.put(question, answers);
    }

    public int addNewAnswersToQuestion(String question, List<String> newAnswers){
        List<String> existingAnswers = getAnswersToQuestion(question);
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
