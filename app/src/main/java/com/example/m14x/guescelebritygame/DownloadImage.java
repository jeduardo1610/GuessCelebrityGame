package com.example.m14x.guescelebritygame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by m14x on 04/06/2016.
 */
public class DownloadImage extends AsyncTask<String,Void,Bitmap> {
    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection= (HttpURLConnection)url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
