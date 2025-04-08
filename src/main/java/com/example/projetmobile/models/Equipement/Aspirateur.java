package com.example.projetmobile.models.Equipement;
import com.example.projetmobile.R;
import com.example.projetmobile.models.Equipement.Appliances;

public class Aspirateur extends Appliances {
    // Liste des icônes (R.drawable.xx)
    // Liste des icônes (R.drawable.xx)
    public Aspirateur(int id, String ref, int conso) {
        super(  id,"Aspirateur", ref, conso);
        this.setIcon(R.drawable.ic_aspirateur);

    }
}