package com.example.jorge_alejandro.busesbusapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by JORGE_ALEJANDRO on 26/03/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SessionTest {
    private String ip;
    @Before
    public void setUp() throws Exception {
        ip="192.168.1.57";
    }

    @After
    public void tearDown() throws Exception {

    }

    /*
    @Test
    public void readFileSession() throws Exception {

    }

    @Test
    public void writeFileSession() throws Exception {

    }
    */

    @Test
    public void logIn() throws Exception {
        String plate = "VCP541";
        String password = "12345";
        Context context= InstrumentationRegistry.getTargetContext();
        Session session=new Session();
        Bus bus=session.logIn(plate,password,context);
        assertEquals(1,bus.getId());
        assertEquals("VCP541",bus.getPlate());
        assertEquals("LUIS",bus.getDriverName());
        assertEquals("EJECUTIVO",bus.getBusType());
        assertEquals(1850,bus.getTicketPrice());
    }

    @Test
    public void invalid1LogIn() throws Exception {
        String plate = "";
        String password = "12345";
        Context context= InstrumentationRegistry.getTargetContext();
        Session session=new Session();
        Bus bus=session.logIn(plate,password,context);
        assertEquals(1,bus.getId());
        assertEquals("VCP541",bus.getPlate());
        assertEquals("LUIS",bus.getDriverName());
        assertEquals("EJECUTIVO",bus.getBusType());
        assertEquals(1850,bus.getTicketPrice());
    }

    @Test
    public void invalid2LogIn() throws Exception {
        String plate = "VCP541";
        String password = "";
        Context context= InstrumentationRegistry.getTargetContext();
        Session session=new Session();
        Bus bus=session.logIn(plate,password,context);
        assertEquals(1,bus.getId());
        assertEquals("VCP541",bus.getPlate());
        assertEquals("LUIS",bus.getDriverName());
        assertEquals("EJECUTIVO",bus.getBusType());
        assertEquals(1850,bus.getTicketPrice());
    }

}