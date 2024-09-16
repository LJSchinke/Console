package org.example.console.exceptions;

public class InvalidAnswerLengthException extends Throwable {

    public InvalidAnswerLengthException(){
        super("Answer exceeds the 255 character limit.");
    }
}
