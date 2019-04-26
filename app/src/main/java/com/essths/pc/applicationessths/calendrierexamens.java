package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.essths.pc.applicationessths.Config.ConfigRetrofit;
import com.essths.pc.applicationessths.model.calendrier;
import com.essths.pc.applicationessths.model.resultatmatiere;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class calendrierexamens extends AppCompatActivity {
    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();
    Spinner test;
    Button btnres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrierexamens);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String uclasse = sharedPreferences.getString("classe", "erreur");
        Toast.makeText(getApplicationContext(),"uclasse"+uclasse,Toast.LENGTH_SHORT).show();

        final RadioGroup rGroup = (RadioGroup)findViewById(R.id.radioGroup);
        Button btnres = (Button) findViewById(R.id.res);


        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    Toast.makeText(getApplicationContext(),checkedRadioButton.getText(),Toast.LENGTH_SHORT).show();
                    String var= (String) checkedRadioButton.getText();
                    Toast.makeText(getApplicationContext(),"var="+var,Toast.LENGTH_SHORT).show();

                }
            }
        });


        final Spinner test =(Spinner)findViewById(R.id.sptest);
        ArrayAdapter<String> adaptertest = new ArrayAdapter<String>(calendrierexamens.this,
                android.R.layout.simple_expandable_list_item_1,getResources().getStringArray(R.array.cal));
        adaptertest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        test.setAdapter(adaptertest);

        final String var1 = (test.getSelectedItem()).toString();
        String lien;





        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String test1=(test.getSelectedItem().toString());

                RadioButton selectedRadioButton = (RadioButton) findViewById(rGroup.getCheckedRadioButtonId());
                String sem = selectedRadioButton.getText().toString();
                // Toast.makeText(getApplicationContext(),""+sem,Toast.LENGTH_LONG).show();




                IEtudiant apii = retrofit.create(IEtudiant.class);
                Call<calendrier> call = apii.getcalendrier(uclasse,sem);


                call.enqueue(new Callback<calendrier>() {
                    @Override
                    public void onResponse(Call<calendrier> call, Response<calendrier> response) {
                        calendrier rm=new calendrier();
                        rm=response.body();


                        String lien="";
                        String varexamen=rm.getExamen().toString();
                        String vards=rm.getDs().toString();


                        if(var1.equals("Examen")){lien=varexamen;}
                        if(var1.equals("DS")){lien=vards;}



                        SharedPreferences sp = getSharedPreferences("liencal", Context.MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("lien", lien);
                        Ed.apply();
                        startActivity(new Intent(calendrierexamens.this,calendrierexamens2.class));

                    }

                    @Override
                    public void onFailure(Call<calendrier> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }



}
