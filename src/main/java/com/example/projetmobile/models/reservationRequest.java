package com.example.projetmobile.models;

public class reservationRequest {
    private String datetime;
    private String appareil;
    private int idhabit;

    public reservationRequest(String datetime, String appareil, int idhabit) {
        this.datetime = datetime;
        this.appareil = appareil;
        this.idhabit = idhabit;
    }
}
