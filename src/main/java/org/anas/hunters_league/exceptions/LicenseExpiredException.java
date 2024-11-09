package org.anas.hunters_league.exceptions;

public class LicenseExpiredException extends RuntimeException {
    public LicenseExpiredException(String message) {
        super(message);
    }
}

