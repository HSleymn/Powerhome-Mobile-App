package com.example.projetmobile;


import static java.util.jar.Pack200.Packer.ERROR;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetmobile.api.ApiService;
import com.example.projetmobile.api.RetrofitClient;
import com.example.projetmobile.models.Equipement.LaveLinge;
import com.example.projetmobile.models.Habitat;
import com.example.projetmobile.models.HabitatRequest;
import com.example.projetmobile.models.HabitatResponse;
import com.example.projetmobile.models.LoginRequest;
import com.example.projetmobile.models.LoginResponse;
import com.example.projetmobile.models.User;
import com.example.projetmobile.models.getUserRequest;
import com.example.projetmobile.models.getUserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHabitatFragment extends Fragment {

    private TextView tvuserName;
    private TextView tvemail;
    private TextView tvHabitatName;
    private TextView tvTotalConsumption;

    private TextView tvArea;
    private TextView tvFloor;
    private int userId;
    private SwipeRefreshLayout swipeRefreshLayout;


    public MyHabitatFragment(int id) {
        this.userId = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_habitat, container, false);
        Habitat habitat;
        connectApi(this.userId, view);



        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Code pour rafraîchir les données
                refreshData(userId, view);
            }
        });



        return view;
    }

    private void setText(View view, User user, Habitat habitat) {

        tvuserName = view.findViewById(R.id.tv_user_name);
        tvemail = view.findViewById(R.id.tv_user_email);


        tvuserName.setText(user.getFirstname() + " " + user.getLastname());
        tvemail.setText(user.getEmail());

        tvHabitatName = view.findViewById(R.id.tv_habitat_name);
        tvFloor = view.findViewById(R.id.tv_floor);
        tvArea = view.findViewById(R.id.tv_area);

        tvTotalConsumption = view.findViewById(R.id.tv_total_consumption);


        tvHabitatName.setText("Habitat: Appartement 10" + Integer.toString(habitat.getId()));
        tvFloor.setText("Etage : " + Integer.toString(habitat.getFloor()));
        tvArea.setText("Surface : " + Integer.toString(habitat.getArea()) + " mètres carrés.");
        tvTotalConsumption.setText("Consommation totale: " + habitat.getTotalConsumption());
        ListView listView = view.findViewById(R.id.equipment_list_view);

        AppliancesAdapter adapter = new AppliancesAdapter(getActivity(), R.layout.item_appliance, habitat.getAppliances());
        listView.setAdapter(adapter);
    }

    public void fetchHabitat(Habitat h, int idHabitat, View view, User user) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        HabitatRequest habitatRequest = new HabitatRequest(idHabitat);

        apiService.getHabitat(habitatRequest).enqueue(new Callback<HabitatResponse>() {
            @Override
            public void onResponse(Call<HabitatResponse> call, Response<HabitatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HabitatResponse reponse = response.body();
                    h.setId(reponse.getId());
                    h.setFloor(reponse.getFloor());
                    h.setArea(reponse.getArea());
                    h.setAppliances(reponse.getAppliancesList());

                }
                setText(view, user, h);

            }

            ;

            @Override
            public void onFailure(Call<HabitatResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur réseau : " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void connectApi(int userId, View view) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        getUserRequest getUserRequest = new getUserRequest(userId);

        apiService.getUser(getUserRequest).enqueue(new Callback<getUserResponse>() {
            public void onResponse(Call<getUserResponse> call, Response<getUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getUserResponse response1 = response.body();
                    User user;
                    user = new User(response1.getIdUser(),
                            response1.getFirstname(),
                            response1.getLastname(),
                            response1.getEmail(),
                            response1.getId_habitat()
                    );

                    Habitat habitat = new Habitat(user.getId_habitat(), user.getFirstname() + " " + user.getLastname());
                    Toast.makeText(getActivity(), "Bienvenue " + user.getFirstname() + " " + user.getLastname() + " !", Toast.LENGTH_SHORT).show();

                    Button btnReservation = view.findViewById(R.id.btnReservation);  // Ton bouton de réservation
                    btnReservation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Remplacer par un Fragment de réservation
                            ReservationFragment reservationFragment = new ReservationFragment(user);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, reservationFragment)  // R.id.fragment_container -> ton conteneur
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });


                    fetchHabitat(habitat, user.getId_habitat(), view, user);

                }
            }

            @Override
            public void onFailure(Call<getUserResponse> call, Throwable t) {
                Log.e("API_ERROR", "Erreur réseau : " + t.getMessage());
            }
        });
    }
    private void refreshData(int userId, View view) {
        // Code pour envoyer une requête et récupérer les nouvelles données
        // Par exemple, une requête réseau pour obtenir les données mises à jour
        connectApi(userId, view);
        // Une fois les données récupérées, assurez-vous de désactiver le rafraîchissement
        swipeRefreshLayout.setRefreshing(false);
    }

}
