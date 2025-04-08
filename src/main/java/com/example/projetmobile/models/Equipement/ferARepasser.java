package com.example.projetmobile.models.Equipement;

import com.example.projetmobile.R;

public class ferARepasser extends Appliances{
    // Liste des icônes (R.drawable.xx)
    public ferARepasser(int id, String ref, int conso) {
        super(  id,"Fer à repasser", ref, conso);
        this.setIcon(R.drawable.ic_fer_a_repasser);

    }
}

