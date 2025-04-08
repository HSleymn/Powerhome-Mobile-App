package com.example.projetmobile;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetmobile.models.Equipement.Appliances;

import java.util.List;

public class AppliancesAdapter extends ArrayAdapter<Appliances> {

    Activity activity;
    List<Appliances> appliances;
    int habitatId;

    public AppliancesAdapter(Activity activity, int itemResourceId, List<Appliances> items) {
        super(activity, itemResourceId, items);

        this.activity = activity;
        this.habitatId = itemResourceId;
        this.appliances = items;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(R.layout.item_appliance, parent, false);
        }

        // Récupération de l'équipement à afficher
        Appliances app = appliances.get(position);

        TextView appname = row.findViewById(R.id.appname);
        TextView reference = row.findViewById(R.id.reference);
        TextView wattage = row.findViewById(R.id.wattage);
        ImageView iconImg = row.findViewById(R.id.icon);

        appname.setText(app.getName());
        reference.setText(app.getReference());
        wattage.setText(Integer.toString(app.getWattage()));

        // Sélection de l'icône en fonction du nom de l'équipement
        int icon = 0;
        switch (app.getName().toLowerCase()) {
            case "aspirateur":
                icon = R.drawable.ic_aspirateur;
                break;
            case "climatiseur":
                icon = R.drawable.ic_climatiseur;
                break;
            case "fer a repasser":
                icon = R.drawable.ic_fer_a_repasser;
                break;
            case "lave linge":
                icon = R.drawable.ic_machine_a_laver;
                break;

        }

        // Appliquer l'icône à l'ImageView
        iconImg.setImageResource(icon);

        return row;
    }

    public void setIcon(int icon,ImageView iconview) {
        iconview.setImageResource(icon);
    }
}
