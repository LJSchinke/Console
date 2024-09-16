package org.example.console.exceptions;

public class InvalidAnswerFormatException extends Throwable {

    public InvalidAnswerFormatException(){
        super("Invalid format. Answers should be enclosed in quotes");
    }
}
