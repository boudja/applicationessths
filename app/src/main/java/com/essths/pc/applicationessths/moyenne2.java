package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class moyenne2 extends AppCompatActivity {
    PDFView pdfres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyenne2);

        pdfres=(PDFView)findViewById(R.id.pdfres);
        SharedPreferences sharedPreferences = getSharedPreferences("lienmoy", Context.MODE_PRIVATE);
        final String lien = sharedPreferences.getString("lien","whatever");
        // new PDFstream().execute("http://www.essths.rnu.tn/FR/listeetudiant1718/SI3TD1TP1.pdf");
        new PDFstream().execute(lien);
    }
    class PDFstream extends AsyncTask<String,Void,InputStream>
    {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try{
                URL url =new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }

            }catch (IOException e)
            {return null;}
            return inputStream;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfres.fromStream(inputStream).load();
        }
    }
}
