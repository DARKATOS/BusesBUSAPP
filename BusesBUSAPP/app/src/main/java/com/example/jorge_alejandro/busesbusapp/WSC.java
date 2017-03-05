package com.example.jorge_alejandro.busesbusapp;

import android.os.AsyncTask;


/**
 * Created by JORGE_ALEJANDRO on 24/02/2017.
 */

public class WSC extends AsyncTask<String, Long, String> {


    @Override
    protected String doInBackground(String... params) {
        try {
            String response= HttpRequest.get(params[0]).accept("application/json").body();
            return response;
        } catch (HttpRequest.HttpRequestException exception) {
            return null;
        }
    }
}
