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

public class BusLocationActivity extends AppCompatActivity {

    LocationManager locationManager;
    Location location;
    boolean activeGps;
    int flag;
    TextView tlatitud;
    TextView tlongitud;
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslocation);
        bus=(Bus)getIntent().getExtras().getSerializable("bus");
        flag=1;
        tlatitud = (TextView) findViewById(R.id.latitud);
        tlongitud = (TextView) findViewById(R.id.longitud);
        getLocation();

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locationUpdate(location);
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
            activeGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (activeGps) {
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
                Log.d("Info: ", "activando localizacion");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locationUpdate(location);
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

    public void locationUpdate(Location location)
    {
        if (location != null) {
            try {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                BusLocation busLocation =new BusLocation(-1, latitude, longitude, bus);

                Log.d("Info", "Actualizando");
                Log.d("Info", String.valueOf(busLocation.getBus().getId()));
                Log.d("Info", String.valueOf(busLocation.getLatitude()));
                Log.d("Info", String.valueOf(busLocation.getLongitude()));

                String url = "http://"+ LoginActivity.ip+":8084/BUSAPP/rest/services/busLocationUpdate/" + busLocation.getBus().getId()+"/"+ busLocation.getLatitude()+"/"+ busLocation.getLongitude();
                String response = new WSC().execute(url).get();
                if (response.equals("Success"))
                {
                    tlatitud.setText(Double.toString(busLocation.getLatitude()));
                    tlongitud.setText(Double.toString(busLocation.getLongitude()));
                }
            }catch(Exception ex)
            {
                Log.d("Error", "Exception: "+ex.toString());
            }
        }
        else
        {
            Log.d("Info: ", "Location null");
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i=new Intent(BusLocationActivity.this,LoginActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {

            if (flag==1)
            {
                String url = "http://"+ LoginActivity.ip+":8084/BUSAPP/rest/services/busLocationDelete/" + bus.getId();
                String response = new WSC().execute(url).get();
                if (response.equals("Success"))
                {
                    Log.d("Error", "Eliminada la ubicacion del bus");
                }
                else
                {
                    Log.d("Error", "No se pudo eliminar la ubicacion del bus");
                }
                flag=0;
            }
            else
            {
                flag++;
            }


        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}
