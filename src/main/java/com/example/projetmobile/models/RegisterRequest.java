// RegisterRequest.java
package com.example.projetmobile.models;

public class RegisterRequest {
    private String FirstName;
    private String LastName;

    private String email;
    private String password;

    public RegisterRequest(String FirstName, String LastName, String email, String password) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.email = email;
        this.password = password;
    }

    // Getters et setters
}
