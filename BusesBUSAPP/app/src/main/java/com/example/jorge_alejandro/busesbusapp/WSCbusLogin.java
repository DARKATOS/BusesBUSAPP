package com.example.jorge_alejandro.busesbusapp;

import android.os.AsyncTask;
import android.util.Log;


/**
 * Created by JORGE_ALEJANDRO on 24/02/2017.
 */

public class WSCbusLogin extends AsyncTask<String, Long, String> {


    @Override
    protected String doInBackground(String... params) {
        try {
            String response= HttpRequest.get(params[0]).accept("application/json").body();
            return response;
        } catch (HttpRequest.HttpRequestException exception) {
            return null;
        }
    }

    protected void onPostExecute(String response) {
        Log.d(response,response);
        if (response=="Success")
        {
            MainActivity.successLogin=true;
        }
        else
        {
            MainActivity.successLogin=false;
        }
        //Gson json=new Gson();
        //String resultado=json.fromJson(response, String.class);
        //MainActivity.texto.setText(response);
    }
}
