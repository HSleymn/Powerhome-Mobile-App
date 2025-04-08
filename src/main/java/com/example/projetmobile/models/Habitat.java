package com.example.projetmobile.models;


import com.example.projetmobile.models.Equipement.Appliances;

import java.util.ArrayList;
import java.util.List;

public class Habitat {
    private int id;
    private int BonusMalus;
    private String residentName;
    private List<Appliances> appliances;
    private int floor;

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    private int area;

    public Habitat(int id, String residentName, int floor, int area, List<Appliances> app) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
        this.area = area;
        this.appliances = app;
    }
    public Habitat(int id, String residentName, int floor) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
        appliances = new ArrayList<>();
    }
    public Habitat(int id, String residentName) {
        this.id = id;
        this.residentName = residentName;
        this.floor = floor;
    }


    public void addAppliance(Appliances app){
        appliances.add(appliances.size(), app);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Appliances> getAppliances() {
        return appliances;
    }

    public void setAppliances(List appliances) {
        this.appliances = appliances;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getTotalConsumption() {
        int total = 0;
        for(Appliances app : appliances){
            total += app.getWattage();
        }
        return total;
    }
    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public int getBonusMalus() {
        return BonusMalus;
    }

    public void setBonusMalus(int bonusMalus) {
        BonusMalus = bonusMalus;
    }


    public void Habitat(){


    }
}
