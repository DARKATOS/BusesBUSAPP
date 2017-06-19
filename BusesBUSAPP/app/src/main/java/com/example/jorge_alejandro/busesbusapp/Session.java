package com.example.jorge_alejandro.busesbusapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by JORGE_ALEJANDRO on 12/03/2017.
 */

public class Session {

    private String ip="181.59.153.198:8085";
    /**
     * read file session: Metodo que permite leer el archivo de sesión de la aplicación para obtener datos y hacer la conexión con el servidor.
     * @param context Contexto donde se realiza la lectura del archivo de sesión.
     * @return Bus si se leyo correctamente el archivo de sesión. Null si hubo algun error al leer el archivo de sesión.
     */
    public Bus readFileSession(Context context)
    {
        try
        {
            File directory = context.getExternalFilesDir(null);
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
                    Bus bus=new Bus(id,plate,null,driverName,busType,ticketPrice);
                    String url="http://"+ip+"/BUSAPP/rest/services/busLogIn/"+bus.getId()+"/"+bus.getPlate();
                    String response=new WSC().execute(url).get();
                    Log.d("info",response);
                    if (response.equals("Success"))
                    {
                        Log.d("Info", "Entro sucess");
                        return bus;
                    }
                    else
                    {
                        Log.d("Info", "Entro failure");
                        return null;
                    }
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        catch (Exception ex)
        {
            Log.d("Error", "entro en excepcion: "+ex.getMessage());
            return null;
        }
    }

    /**
     * write file session: Metodo que la escritura del archivo de sesión para la aplicación.
     * @param bus con el que se realiza la escritura del archivo se sesión.
     * @param context Contexto donde se realiza la escritura del archivo de sesión.
     */
    public void writeFileSession(Bus bus, Context context)
    {
        try
        {
            File directory = context.getExternalFilesDir(null);
            File file = new File(directory.getAbsolutePath(), "sessioninformation.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(bus.getId()+"\n"+bus.getPlate()+"\n"+bus.getDriverName()+"\n"+bus.getBusType()+"\n"+bus.getTicketPrice());
            outputStreamWriter.close();
        }
        catch (Exception ex)
        {
            Toast toast = Toast.makeText(context,"Error: "+ex.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Log in: Metodo que permite el inicio de sesión del bus.
     * @param plate Placa del bus.
     * @param password Contraseña de la cuenta del bus.
     * @param context Contexto donde se realiza el inicio sesión.
     * @return Bus si el inicio de sesion se realizo correctamente. Null si hubo algun error en los datos de inicio de sesión.
     */
    public Bus logIn(String plate, String password, Context context)
    {
        try {
            Bus bus=new Bus(-1,plate,password,null,null,-1);
            if (bus.getPlate().equals("") || bus.getPassword().equals("")) {
                Toast toast1 = Toast.makeText(context.getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT);
                toast1.show();
                return null;
            } else {

                String url = "http://"+ip+"/BUSAPP/rest/services/busLogInRegister/" + bus.getPlate() + "/" + bus.getPassword();
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                bus=json.fromJson(response, Bus.class);
                if (bus != null) {
                    writeFileSession(bus, context);
                    return bus;
                } else {
                    Toast toast1 = Toast.makeText(context, "No existe el bus", Toast.LENGTH_SHORT);
                    toast1.show();
                    return null;
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
            return null;
        }
    }
}
