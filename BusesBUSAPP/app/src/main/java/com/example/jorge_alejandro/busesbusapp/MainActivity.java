package com.example.jorge_alejandro.busesbusapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    private Button iniciarSesion;
    public static boolean successLogin;
    public static Bus bus;

    private EditText editTextPlate;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (leerArchivo())
        {
            Intent i=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(i);
        }
        else
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
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

        //Aplicativo para los buses, obtener coordenadas y cambios de coordenadas de acuerdo a su movimiento en intervalos de tiempo de actualizacion
    }

    public boolean logIn()
    {
        String plate=editTextPlate.getText().toString();
        String password=editTextPassword.getText().toString();
        if (plate.equals("") || password.equals(""))
        {
            Toast toast1 = Toast.makeText(getApplicationContext(),"Campos vacios", Toast.LENGTH_SHORT);
            toast1.show();
            return false;
        }
        else
        {
            String url="http://192.168.1.57:8084//BUSAPP/rest/services/busLoginRegister/"+plate+"/"+password;
            new WSCbusLoginRegister().execute(url);
            if (bus!=null)
            {
                writeFile(bus);
                return true;
            }
            else
            {
                Toast toast1 = Toast.makeText(getApplicationContext(),"No existe el bus", Toast.LENGTH_SHORT);
                toast1.show();
                return false;
            }
        }
    }

    public boolean leerArchivo()
    {
        successLogin=false;
        BufferedReader read=null;
        try
        {
            read =new BufferedReader(new InputStreamReader(openFileInput("sessionInformation.txt")));

            String text = read.readLine();
            if(text!=null)
            {
                String idbus=text;
                text=read.readLine();
                String plate=text;
                String url="http://192.168.1.57:8084//BUSAPP/rest/services/busLogin/"+idbus+"/"+plate;
                new WSCbusLogin().execute(url);
                if (successLogin)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        catch (Exception ex)
        {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast toast = Toast.makeText(getApplicationContext(),"Error: "+ex.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }

    public void writeFile(Bus bus)
    {
        try
        {
            FileOutputStream fout=openFileOutput("sessionInformation.txt", Context.MODE_PRIVATE);
            PrintWriter printWriter= new PrintWriter(fout, true);
            printWriter.write("");
            printWriter.println(bus.getId()+"\n");
            printWriter.println(bus.getPlate()+"\n");
            printWriter.println(bus.getDriverName()+"\n");
            printWriter.println(bus.getType()+"\n");
            printWriter.println(bus.getTicketPrice());
            printWriter.close();
            fout.close();
        }
        catch (Exception ex)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Error: "+ex.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
