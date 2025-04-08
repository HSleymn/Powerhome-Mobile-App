package com.example.projetmobile.models.Equipement;

import com.example.projetmobile.R;

public class Climatiseur extends Appliances{
    // Liste des icônes (R.drawable.xx)
    public Climatiseur(int id, String ref, int conso) {
        super(  id,"Climatiseur", ref, conso);
        this.setIcon(R.drawable.ic_climatiseur);

    }
}