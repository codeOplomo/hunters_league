package org.anas.hunters_league.exceptions;

public class MaxParticipantsReachedException extends RuntimeException {
    public MaxParticipantsReachedException(String message) {
        super(message);
    }
}
