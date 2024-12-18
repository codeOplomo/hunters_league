package org.anas.hunters_league.service.dto;

public class RegisterDTO {
    private String message;
    private String email;

    public RegisterDTO(String message, String email) {
        this.message = message;
        this.email = email;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
