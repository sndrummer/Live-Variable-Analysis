package edu.byu.yc;

public class InvalidVisitException extends RuntimeException {

    public InvalidVisitException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public InvalidVisitException(String errorMessage) {
        super(errorMessage);
    }
}