package org.example.console;

import java.util.HashMap;
import java.util.List;
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

    public void addQuestionAndAnswer(String question, List<String> answers){

        QuestionAnswerMap.put(question, answers);
    }

    public Set<String> getQuestions (){
        return QuestionAnswerMap.keySet();
    }

    public void resetSystem(){
        QuestionAnswerMap=new HashMap<String, List<String>>();
    }
}
