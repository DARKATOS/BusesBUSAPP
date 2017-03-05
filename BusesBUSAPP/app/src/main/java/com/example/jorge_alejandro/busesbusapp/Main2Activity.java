package com.example.jorge_alejandro.busesbusapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    double latitude;
    double longitude;
    LocationManager locationManager;
    Location location;
    boolean gpsActivo;
    boolean flag;
    TextView tlatitud;
    TextView tlongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        flag=false;
        tlatitud = (TextView) findViewById(R.id.latitud);
        tlongitud = (TextView) findViewById(R.id.longitud);
        getLocation();

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            flag=true;

            if (gpsActivo) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);
                location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                actualizarUbicacion(location);
            }
            else
            {
                Toast toast = Toast.makeText(this, "Encienda el GPS", Toast.LENGTH_LONG);
                toast.show();
            }

        }catch (Exception e)
        {
            Toast toast = Toast.makeText(this, "Mensaje: "+e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void actualizarUbicacion(Location location)
    {
        if (location != null) {
            try {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                String url = "http://192.168.1.57:8084/BUSAPP/rest/services/busLocationUpdate/" + MainActivity.bus.getId()+"/"+latitude+"/"+longitude;
                String response = new WSC().execute(url).get();
                if (response.equals("Success"))
                {
                    tlatitud.setText(Double.toString(latitude));
                    tlongitud.setText(Double.toString(longitude));
                    Log.d("Errror", "Actualizando ubicacion");
                }
            }catch(Exception ex)
            {
                Log.d("Error", "Exception: "+ex.toString());
            }
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i=new Intent(Main2Activity.this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (flag)
            {
                String url = "http://192.168.1.57:8084/BUSAPP/rest/services/busLocationDelete/" + MainActivity.bus.getId();
                String response = new WSC().execute(url).get();
                if (response.equals("Success"))
                {
                    Log.d("Error", "Eliminada la ubicacion del bus");
                }
                else
                {
                    Log.d("Error", "No se pudo eliminar la ubicacion del bus");
                }
            }
            flag=true;

        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}
