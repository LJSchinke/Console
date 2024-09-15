package org.example.console.exceptions;

public class InvalidQuestionLengthException extends Throwable {

    public InvalidQuestionLengthException(){
        super("Question exceeds the 255 character limit.");
    }
}
