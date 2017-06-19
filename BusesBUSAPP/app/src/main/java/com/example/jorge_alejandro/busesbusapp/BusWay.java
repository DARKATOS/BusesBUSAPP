package com.example.jorge_alejandro.busesbusapp;

/**
 * Created by JORGE_ALEJANDRO on 17/06/2017.
 */

public class BusWay {

    private int idbusWay;
    private String wayName;
    private Bus bus;

    public BusWay(int idbusWay, String wayName, Bus bus) {
        this.idbusWay = idbusWay;
        this.wayName = wayName;
        this.bus = bus;
    }

    public BusWay(int idbusWay) {
        this.idbusWay = idbusWay;
    }

    public int getIdbusWay() {
        return idbusWay;
    }


    public Bus getBus() {
        return bus;
    }

    public String getWayName() {
        return wayName;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public void setWayName(String wayName) {
        this.wayName = wayName;
    }
}
