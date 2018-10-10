package com.example.blueravine.permissionmanage;

import java.util.ArrayList;

/**
 * Created by blue rabbit on 2018-01-27.
 */

public class PackageVal //Klasa do przechowywania nazwy aplikacji, nazwy paczki oraz listy permisji
{
    String name;
    String packageName;
    ArrayList<String> permissions = new ArrayList<>();
    boolean locationpermis;
    boolean camerapermis;
    boolean micpermis;
    boolean contactspermis;

    public boolean getLocationpermis() {
        return locationpermis;
    }

    public void setLocationpermis(boolean locationpermis) {
        this.locationpermis = locationpermis;
    }

    public boolean getCamerapermis() {
        return camerapermis;
    }

    public void setCamerapermis(Boolean camerapermis) {
        this.camerapermis = camerapermis;
    }

    public boolean getMicpermis() {
        return micpermis;
    }

    public void setMicpermis(Boolean micpermis) {
        this.micpermis = micpermis;
    }

    public Boolean getContactspermis() {
        return contactspermis;
    }

    public void setContactspermis(Boolean contactspermis) {
        this.contactspermis = contactspermis;
    }


    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }
}
