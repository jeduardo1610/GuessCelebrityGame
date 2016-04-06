package com.example.m14x.guescelebritygame;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by m14x on 04/06/2016.
 */
public class DownloadTask extends AsyncTask <String,Void,String> {
    @Override
    protected String doInBackground(String... params) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try{
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while(data != -1){
                char current = (char) data;
                result += current;
                data = reader.read();
            }
            return result;

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}


