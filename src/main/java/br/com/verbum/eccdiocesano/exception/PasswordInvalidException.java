package br.com.verbum.eccdiocesano.exception;

public class PasswordInvalidException extends Exception {
    public PasswordInvalidException(String message) {
        super(message);
    }
}