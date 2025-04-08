package com.example.projetmobile.models.Equipement;

import com.example.projetmobile.R;

public class LaveLinge extends Appliances{
    // Liste des icônes (R.drawable.xx)
    public LaveLinge(int id, String ref, int conso) {
        super(  id,"Machine à laver", ref, conso);
        this.setIcon(R.drawable.ic_machine_a_laver);

    }
}

