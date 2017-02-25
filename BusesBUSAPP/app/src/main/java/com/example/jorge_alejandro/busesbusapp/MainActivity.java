package com.example.jorge_alejandro.busesbusapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double latitud;
    double longitud;
    LocationManager locationManager;
    Location location;
    boolean gpsActivo;

    TextView tlatitud;
    TextView tlongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tlatitud = (TextView) findViewById(R.id.latitud);
        tlongitud = (TextView) findViewById(R.id.longitud);
        getLocation();
        //Aplicativo para los buses, obtener coordenadas y cambios de coordenadas de acuerdo a su movimiento en intervalos de tiempo de actualizacion
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
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
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            tlatitud.setText(Double.toString(latitud));
            tlongitud.setText(Double.toString(longitud));
        }
    }
}
