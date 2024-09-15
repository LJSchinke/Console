package org.example.console.exceptions;

public class InvalidQuestionFormatException extends Throwable {

    public InvalidQuestionFormatException(){
        super("Invalid format. Make sure the question ends with a '?'.");
    }
}
