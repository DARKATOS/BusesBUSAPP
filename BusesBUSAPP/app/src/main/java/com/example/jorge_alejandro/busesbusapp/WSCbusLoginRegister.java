package com.example.jorge_alejandro.busesbusapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by JORGE_ALEJANDRO on 25/02/2017.
 */

public class WSCbusLoginRegister extends AsyncTask<String, Long, String> {
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
        if (response!=null)
        {
            Gson json=new Gson();
            MainActivity.bus=json.fromJson(response, Bus.class);
        }
        else
        {
            MainActivity.bus=null;
        }
    }
}
