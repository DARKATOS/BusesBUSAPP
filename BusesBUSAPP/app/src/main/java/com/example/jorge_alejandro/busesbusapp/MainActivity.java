package com.example.jorge_alejandro.busesbusapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private Button iniciarSesion;
    public static Bus bus;

    private EditText editTextPlate;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (readFileSession())
        {
            if (busLocationRegister())
            {
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }

        }
        else
        {
            editTextPlate=(EditText)findViewById(R.id.editText);
            editTextPassword=(EditText)findViewById(R.id.editText2);
            iniciarSesion=(Button)findViewById(R.id.button1);
            iniciarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (logIn())
                    {
                        Intent i=new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(i);
                    }
                }
            });
        }
        //Aplicativo para los buses, obtener coordenadas y cambios de coordenadas de acuerdo a su movimiento en intervalos de tiempo de actualizacion
    }

    public boolean busLocationRegister()
    {
        try {
            String url = "http://192.168.1.57:8084/BUSAPP/rest/services/busLocationRegister/" + bus.getId();
            String response = new WSC().execute(url).get();
            if (response.equals("Success"))
            {
                return true;
            }
            else
            {
                return false;
            }

        }catch (Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
            return false;
        }

    }

    public boolean logIn()
    {
        try {
            String plate = editTextPlate.getText().toString();
            String password = editTextPassword.getText().toString();
            if (plate.equals("") || password.equals("")) {
                Toast toast1 = Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT);
                toast1.show();
                return false;
            } else {

                String url = "http://192.168.1.57:8084/BUSAPP/rest/services/busLogInRegister/" + plate + "/" + password;
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                bus=json.fromJson(response, Bus.class);
                if (bus != null) {
                    writeFileSession(bus);
                    return true;
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "No existe el bus", Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
            return false;
        }
    }

    public boolean readFileSession()
    {
        try
        {
            File directory = getExternalFilesDir(null);
            File file = new File(directory.getAbsolutePath(), "sessioninformation.txt");
            if (file.exists())
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String text = bufferedReader.readLine();
                if(text!=null)
                {
                    int id=Integer.parseInt(text);
                    text=bufferedReader.readLine();
                    String plate=text;
                    text=bufferedReader.readLine();
                    String driverName=text;
                    text=bufferedReader.readLine();
                    String busType=text;
                    text=bufferedReader.readLine();
                    int ticketPrice=Integer.parseInt(text);
                    bufferedReader.close();
                    bus=new Bus(id,plate,null,driverName,busType,ticketPrice);
                    String url="http://192.168.1.57:8084/BUSAPP/rest/services/busLogIn/"+bus.getId()+"/"+bus.getPlate();
                    String response=new WSC().execute(url).get();
                    Log.d("info",response);
                    if (response.equals("Success"))
                    {
                        Log.d("Info", "Entro sucess");
                        return true;
                    }
                    else
                    {
                        Log.d("Info", "Entro failure");
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
            Log.d("Info", "Leyo o no leyo");
        }
        catch (Exception ex)
        {
            Log.d("Error", "entro en excepcion: "+ex.getMessage());
        }
        return false;
    }

    public void writeFileSession(Bus bus)
    {
        try
        {
            File directory = getExternalFilesDir(null);
            File file = new File(directory.getAbsolutePath(), "sessioninformation.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(bus.getId()+"\n"+bus.getPlate()+"\n"+bus.getDriverName()+"\n"+bus.getBusType()+"\n"+bus.getTicketPrice());
            outputStreamWriter.close();
        }
        catch (Exception ex)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Error: "+ex.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
