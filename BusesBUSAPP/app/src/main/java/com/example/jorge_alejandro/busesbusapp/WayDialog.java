package com.example.jorge_alejandro.busesbusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WayDialog extends AppCompatActivity {

    private String ip="181.59.153.198:8085";
    EditText editTextWay;
    Button buttonAceppt;
    private Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_dialog);
        editTextWay=(EditText) findViewById(R.id.way);
        buttonAceppt=(Button)findViewById(R.id.accept);
        bus=(Bus)getIntent().getExtras().getSerializable("bus");

        buttonAceppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                String way = editTextWay.getText().toString();
                String url="http://"+ip+"/BUSAPP/rest/services/busWayRegister/"+bus.getId();
                String response=new WSC().execute(url).get();
                Log.d("info",response);
                if (response.equals("Success"))
                {
                    Log.d("Info", "Entro sucess");
                }
                else
                {
                    Log.d("Info", "Entro failure");
                }
            }catch (Exception ex)
            {
                Log.d("Error", "entro en excepcion: "+ex.getMessage());
            }

            }
        });
    }
}
