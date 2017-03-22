package com.example.jorge_alejandro.busesbusapp;

/**
 * Created by JORGE_ALEJANDRO on 12/03/2017.
 */

public class BusLocation {
    private int id;
    private double latitude;
    private double longitude;
    private Bus bus;

    public BusLocation(int id, double latitude, double longitude, Bus bus) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bus = bus;
    }

    public Bus getBus() {
        return bus;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}
