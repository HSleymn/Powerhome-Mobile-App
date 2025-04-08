package com.example.projetmobile;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetmobile.models.Equipement.Appliances;
import com.example.projetmobile.models.Habitat;

import java.util.List;

public class HabitatsAdapter extends ArrayAdapter<Habitat> {

    Activity activity;
    List<Habitat> habitats;
    int habitatId;

    public HabitatsAdapter(Activity activity, int itemResourceId, List<Habitat> items) {

        super(activity, itemResourceId, items);

        this.activity = activity;
        this.habitatId = itemResourceId;
        this.habitats = items;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(R.layout.item_habitat, parent, false);
        }

        Habitat habitat = habitats.get(position);

        TextView residentName = row.findViewById(R.id.residentName);
        TextView equipmentCount = row.findViewById(R.id.equipmentCount);
        TextView floor = row.findViewById(R.id.etage);
        LinearLayout equipmentIconsContainer = row.findViewById(R.id.equipmentLayout);

        residentName.setText(habitat.getResidentName());

        equipmentCount.setText(habitat.getAppliances().size() + " équipements");
        floor.setText(""+habitat.getFloor());
        equipmentIconsContainer.removeAllViews();

        if (habitat.getAppliances().isEmpty()) {
            equipmentIconsContainer.setVisibility(View.GONE);
        } else {
            equipmentIconsContainer.setVisibility(View.VISIBLE);
            int icon = 0;
            for (Appliances appliance : habitat.getAppliances()) {
                if(appliance.getName().toLowerCase().equals("aspirateur")){
                    icon = R.drawable.ic_aspirateur;
                }
                else if(appliance.getName().toLowerCase().equals("climatiseur")){
                    icon = R.drawable.ic_climatiseur;
                }
                else if(appliance.getName().toLowerCase().equals("fer a repasser")){
                    icon = R.drawable.ic_fer_a_repasser;
                }
                else if(appliance.getName().toLowerCase().equals("lave linge")){
                    icon = R.drawable.ic_machine_a_laver;
                }
                Log.d("HabitatAdapter", "Ajout de l'icône : " + appliance.getName().getClass().getName());

                setIcon(icon, equipmentIconsContainer);
            }
        }
        return row;
    }
    public void setIcon(int icon,LinearLayout equipmentIconsContainer) {
        ImageView iconview = new ImageView(activity);
        iconview.setImageResource(icon);
        iconview.setLayoutParams(new ViewGroup.LayoutParams(60, 60));
        equipmentIconsContainer.addView(iconview);
    }
}
