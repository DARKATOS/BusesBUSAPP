package com.example.jorge_alejandro.busesbusapp;

/**
 * Created by JORGE_ALEJANDRO on 25/02/2017.
 */

public class Bus {

    private int id;
    private String plate;
    private String password;
    private String driverName;
    private String type;
    private int ticketPrice;

    public Bus(int id, String placa, String contrasena, String nombreConductor, String tipo, int valorPasaje) {
        this.id = id;
        this.plate = placa;
        this.password=contrasena;
        this.driverName = nombreConductor;
        this.type = tipo;
        this.ticketPrice = valorPasaje;
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

    public String getType() {
        return type;
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

    public void setType(String type) {
        this.type = type;
    }


    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
