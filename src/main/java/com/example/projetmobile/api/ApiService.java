// ApiService.java
package com.example.projetmobile.api;

import com.example.projetmobile.models.Equipement.Appliances;
import com.example.projetmobile.models.Habitat;
import com.example.projetmobile.models.HabitatRequest;
import com.example.projetmobile.models.HabitatResponse;
import com.example.projetmobile.models.LoginRequest;
import com.example.projetmobile.models.LoginResponse;
import com.example.projetmobile.models.RegisterRequest;
import com.example.projetmobile.models.RegisterResponse;
import com.example.projetmobile.models.getHabitatsAllRequest;
import com.example.projetmobile.models.getUserRequest;
import com.example.projetmobile.models.getUserResponse;
import com.example.projetmobile.models.getHabitatsAllResponse;
import com.example.projetmobile.models.reservationRequest;
import com.example.projetmobile.models.reservationResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login.php") // Assurez-vous que le chemin est correct
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/register.php")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("/getHabitats.php")
    Call<HabitatResponse> getHabitat(@Body HabitatRequest id);

    @POST("/getUser.php")
    Call<getUserResponse> getUser(@Body getUserRequest id);

    @POST("/getHabitatsAll.php")
    Call<List<Habitat>> getHabitatsAll(@Body getHabitatsAllRequest id);

    @POST("/setReservation.php")
    Call<reservationResponse> setReservation(@Body reservationRequest res);

}
