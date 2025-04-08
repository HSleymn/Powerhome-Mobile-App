package com.example.projetmobile.models;

import com.example.projetmobile.models.Equipement.Appliances;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HabitatResponse {

    @SerializedName("id")

    private int id;
    @SerializedName("floor")

    private int floor;
    @SerializedName("area")

    private int area;
    @SerializedName("listApp")

    private List<Appliances> appliancesList;

    public List<Appliances> getAppliancesList() {
        return appliancesList;
    }

    public int getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public int getArea() {
        return area;
    }
}
