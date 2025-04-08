package com.example.projetmobile.models.Equipement;

public class Appliances {
    private int id = 1; // Liste des icônes (R.drawable.xx)
    private String name; // Liste des icônes (R.drawable.xx)
    private String reference; // Liste des icônes (R.drawable.xx)
    private int wattage; // Liste des icônes (R.drawable.xx)

 // Liste des icônes (R.drawable.xx)
 private int icon;


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Appliances() {
    }

    public  Appliances(int id, String name, String ref, int conso ) {
        this.id = id;
        this.wattage = conso;
        this.reference = ref;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getWattage() {
        return wattage;
    }

    public void setWattage(int wattage) {
        this.wattage = wattage;
    }


}
