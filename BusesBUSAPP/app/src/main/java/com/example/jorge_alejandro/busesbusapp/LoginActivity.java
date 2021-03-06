package com.example.jorge_alejandro.busesbusapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public static String ip="181.59.153.198:8085";

    private Button iniciarSesion;
    private EditText editTextPlate;
    private EditText editTextPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Session session= new Session();
        Bus bus=session.readFileSession(getApplicationContext());
        if (bus!=null)
        {
            if (busLocationRegister(bus))
            {
                Intent i=new Intent(LoginActivity.this,BusLocationActivity.class);
                i.putExtra("bus",bus);
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
                    String plate = editTextPlate.getText().toString();
                    String password = editTextPassword.getText().toString();
                    Bus bus=session.logIn(plate, password, getApplicationContext());
                    if (bus!=null)
                    {
                        Intent i=new Intent(LoginActivity.this,BusLocationActivity.class);
                        i.putExtra("bus", bus);
                        startActivity(i);
                    }
                }
            });
        }
        //Aplicativo para los buses, obtener coordenadas y cambios de coordenadas de acuerdo a su movimiento en intervalos de tiempo de actualizacion
    }

    /**
     * bus location register: Metodo que permite registrar la primera ubicacion de registro del bus en el sistema.
     * @param bus Objeto Bus con el que se va a registrar la ubicación.
     * @return True si se registro correctamente la ubicacion. False si hubo algun error al registrar la primera ubicacion de registro.
     */
    public boolean busLocationRegister(Bus bus)
    {
        try {
            Log.d("Info: ", "id del bus: "+bus.getId());
            String url = "http://"+ip+"/BUSAPP/rest/services/busLocationRegister/" + bus.getId();
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
}
