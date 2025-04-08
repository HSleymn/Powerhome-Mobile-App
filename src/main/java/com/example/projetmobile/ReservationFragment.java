package com.example.projetmobile;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projetmobile.api.ApiService;
import com.example.projetmobile.api.RetrofitClient;
import com.example.projetmobile.models.User;
import com.example.projetmobile.models.getUserRequest;
import com.example.projetmobile.models.getUserResponse;
import com.example.projetmobile.models.reservationRequest;
import com.example.projetmobile.models.reservationResponse;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFragment extends Fragment {

    private CalendarView calendarView;
    private Button btnConfirmReservation;
    private String selectedDateTime;
    private Spinner spinnerAppliances;
    private List<String> appliancesList = new ArrayList<>();  // Liste des appareils
    private String selectedAppliance;

    private User user;

    public ReservationFragment(User user){
        this.user = user;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        spinnerAppliances = view.findViewById(R.id.spinner_appliances);

        // Ajoute des appareils fictifs pour tester
        appliancesList.add("Lave linge");
        appliancesList.add("Climatiseur");
        appliancesList.add("Aspirateur");
        appliancesList.add("Fer a repasser");


        // Crée un ArrayAdapter pour le Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, appliancesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAppliances.setAdapter(adapter);

        // Gestion de la sélection dans le Spinner
        spinnerAppliances.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedAppliance = appliancesList.get(position);  // Appareil sélectionné
                Log.d("Reservation", "Appareil sélectionné : " + selectedAppliance);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Si aucune sélection n'est effectuée
            }
        });
        calendarView = view.findViewById(R.id.calendarView);
        btnConfirmReservation = view.findViewById(R.id.btn_confirm_reservation);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Formater la date sélectionnée et l'afficher
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                // Par exemple, afficher la date sélectionnée dans un Toast

                // Ajouter la logique pour choisir l'heure ou réserver
                showTimePickerDialog(selectedDate);
            }
        });

        btnConfirmReservation.setOnClickListener(v -> {
            // Envoi de la réservation via l'API
            sendReservationToApi(calendar , formatter);
        });

        return view;
    }

    private void showTimePickerDialog(String selectedDate) {
        // Afficher un TimePicker pour sélectionner l'heure
        TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> {
                    String selectedTime = String.format("%02d:%02d:00", hourOfDay, minute);  // Format HH:mm:ss
                    selectedDateTime = selectedDate + " " + selectedTime;  // Format SQL DATETIME

                    Toast.makeText(getActivity(), "Date sélectionnée: " + selectedDateTime, Toast.LENGTH_SHORT).show();

                },
                12, 0, true).show(getChildFragmentManager(), "timePicker");
    }

    private void sendReservationToApi(Calendar calendar, SimpleDateFormat formatter) {
        // Code pour envoyer la réservation à l'API
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        if(selectedDateTime == null || selectedDateTime.isEmpty()){
        String defaultTime = formatter.format(calendar.getTime());
        selectedDateTime= defaultTime;
        }


        reservationRequest reservationRequest = new reservationRequest(selectedDateTime,selectedAppliance ,user.getId_habitat());

        apiService.setReservation(reservationRequest).enqueue(new Callback<reservationResponse>() {
            @Override
            public void onResponse(Call<reservationResponse> call, Response<reservationResponse> response) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(Call<reservationResponse> call, Throwable t) {

            }
        });


        Log.d("Reservation", "Date et Heure envoyées : " + selectedDateTime);
    }
}
