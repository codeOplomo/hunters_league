package org.anas.hunters_league.exceptions;

public class CredentialsAlreadyExistException extends RuntimeException {
    public CredentialsAlreadyExistException(String message) {
        super(message);
    }
}
