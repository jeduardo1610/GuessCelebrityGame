package com.example.m14x.guescelebritygame;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> imageLink = new ArrayList<String>();
    ArrayList<String> celebName = new ArrayList<String>();
    int chosenCeleb = 0;
    ImageView celebImageView;
    int locationCorrectAnswer = 0;
    String[] answers = new String[4];
    Button button0, button1, button2,button3;
    public ArrayList<String> getLink(String data) {
        String[] resultSplitted = data.split("<div class=\"sidebarContainer\">");
        ArrayList<String> link = new ArrayList<String>();
        Pattern p = Pattern.compile("img src=\"(.*?)\"");
        Matcher m = p.matcher(resultSplitted[0]);

        while (m.find()) {
            link.add(m.group(1));
        }

        return link;
    }

    public ArrayList<String> getArtist(String data) {
        String[] resultSplitted = data.split("<div class=\"sidebarContainer\">");
        ArrayList<String> artist = new ArrayList<String>();
        Pattern p = Pattern.compile("img src=\"(.*?)\"");
        Matcher m = p.matcher(resultSplitted[0]);
        p = Pattern.compile("alt=\"(.*?)\"");
        m = p.matcher(resultSplitted[0]);

        while (m.find()) {
            artist.add(m.group(1));
        }
        return artist;
    }

    public void celebChosen(View view) {
        if(view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))){
            Toast.makeText(getApplicationContext(),"Correct!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Wrong, It was "+ celebName.get(chosenCeleb),Toast.LENGTH_LONG).show();
        }
        createQuestion();
    }
    public void createQuestion(){
        Random random = new Random();
        chosenCeleb = random.nextInt(imageLink.size());
        DownloadImage  downloadImage = new DownloadImage();
        Bitmap celebImage;
        try {
            celebImage = downloadImage.execute(imageLink.get(chosenCeleb)).get();

            celebImageView.setImageBitmap(celebImage);
            locationCorrectAnswer = random.nextInt(4);
            int locationIncorrectAnswer;
            for (int j = 0; j < 4; j++) {
                if (j == locationCorrectAnswer) {
                    answers[j] = celebName.get(chosenCeleb);
                } else {
                    locationIncorrectAnswer = random.nextInt(imageLink.size());

                    while (locationIncorrectAnswer == locationCorrectAnswer) {
                        locationIncorrectAnswer = random.nextInt(imageLink.size());
                    }

                    answers[j] = celebName.get(locationIncorrectAnswer);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        celebImageView = (ImageView) findViewById(R.id.imageView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("http://www.posh24.com/celebrities").get();
            imageLink = getLink(result);
            celebName = getArtist(result);

            } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

        createQuestion();

    }
}
