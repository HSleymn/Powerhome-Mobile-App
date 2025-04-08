package com.example.projetmobile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetmobile.api.ApiService;
import com.example.projetmobile.api.RetrofitClient;
import com.example.projetmobile.models.Equipement.Appliances;
import com.example.projetmobile.models.Equipement.Aspirateur;
import com.example.projetmobile.models.Equipement.LaveLinge;
import com.example.projetmobile.models.Habitat;
import com.example.projetmobile.models.HabitatRequest;
import com.example.projetmobile.models.HabitatResponse;
import com.example.projetmobile.models.getHabitatsAllRequest;
import com.example.projetmobile.models.getHabitatsAllResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListHabitatsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_habitats, container, false);

        ListView listView = view.findViewById(R.id.habitatListView);

        connectApi(listView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Code pour rafraîchir les données
                refreshData( listView);
            }
        });

        return view;
    }

    public void connectApi( ListView listView){
        List<Habitat> habitats = new ArrayList<>();
        getHabitatsAllRequest id= new getHabitatsAllRequest();
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getHabitatsAll(id).enqueue(new Callback<List<Habitat>>(){
            @Override
            public void onResponse(Call<List<Habitat>> call, Response<List<Habitat>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Habitat> objects = response.body();

                    // Traitez la liste d'objets ici
                    for (Habitat obj : objects) {
                        habitats.add(new Habitat(obj.getId(), obj.getResidentName(), obj.getFloor(), obj.getArea(), obj.getAppliances()));
                    }

                    HabitatsAdapter adapter = new HabitatsAdapter(getActivity(), R.layout.item_habitat, habitats);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Habitat habitat = habitats.get(position);
                            Toast.makeText(getActivity(), habitat.getResidentName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Habitat>> call, Throwable t) {
                Log.e("API_ERROR", "Erreur réseau : " + t.getMessage());

                Toast.makeText(getActivity(), "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public void refreshData(ListView listView){
        connectApi(listView);
        swipeRefreshLayout.setRefreshing(false);

    }

}
