package com.example.projetmobile.models;


public class User {
    private int id;

    private String firstname;

    private String lastname;


    private String email;

    private int id_habitat;
    public User(int id, String firstname, String lastname, String email, int id_habitat) {
        this.id = id;
        this.firstname = firstname;
        this.email = email;
        this.lastname = lastname;
        this.id_habitat = id_habitat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setId_habitat(int id_habitat) {
        this.id_habitat = id_habitat;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_habitat() {
        return id_habitat;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }
}
