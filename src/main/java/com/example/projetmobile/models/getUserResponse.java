package com.example.projetmobile.models;

import com.google.gson.annotations.SerializedName;

public class getUserResponse {
    @SerializedName("idUser")

    private int idUser;

    @SerializedName("fName")

    private String firstname;
    @SerializedName("lName")

    private String lastname;
    @SerializedName("email")

    private String email;
    @SerializedName("idHabitat")

    private int id_habitat;

    public String getFirstname() {
        return firstname;
    }

    public int getIdUser() {
        return idUser;
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
