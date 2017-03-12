package com.example.jorge_alejandro.busesbusapp;

import java.io.Serializable;

/**
 * Created by JORGE_ALEJANDRO on 25/02/2017.
 */

public class Bus implements Serializable{

    private int id;
    private String plate;
    private String password;
    private String driverName;
    private String busType;
    private int ticketPrice;

    public Bus(int id, String plate, String password, String driverName, String busType, int ticketPrice) {
        this.id = id;
        this.plate = plate;
        this.password = password;
        this.driverName = driverName;
        this.busType = busType;
        this.ticketPrice = ticketPrice;
    }



    public int getId() {
        return id;
    }

    public String getPlate() {
        return plate;
    }


    public String getDriverName() {
        return driverName;
    }

    public String getBusType() {
        return busType;
    }



    public String getPassword() {
        return password;
    }


    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }



    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
