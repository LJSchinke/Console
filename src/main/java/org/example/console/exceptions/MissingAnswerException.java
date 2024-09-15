package org.example.console.exceptions;

public class MissingAnswerException extends Throwable {

    public MissingAnswerException(){
        super("You need to provide at least one answer.");
    }
}
