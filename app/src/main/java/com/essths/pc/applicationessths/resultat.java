package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.essths.pc.applicationessths.Config.ConfigRetrofit;
import com.essths.pc.applicationessths.model.matiere;
import com.essths.pc.applicationessths.model.resultatmatiere;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class resultat extends AppCompatActivity {

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();
    Spinner matiere1;
    Button btnres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        final RadioGroup rGroup = (RadioGroup)findViewById(R.id.semestre);
        Button btnres = (Button) findViewById(R.id.res);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String uclasse = sharedPreferences.getString("classe", "erreur");
        Toast.makeText(getApplicationContext(),"uclasse"+uclasse,Toast.LENGTH_SHORT).show();

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    Toast.makeText(getApplicationContext(),checkedRadioButton.getText(),Toast.LENGTH_SHORT).show();
                    final String semestre= (String) checkedRadioButton.getText();

                    matiere1 = (Spinner) findViewById(R.id.spmatiere);
                    IEtudiant api = retrofit.create(IEtudiant.class);
                    Call<List<matiere>> call = api.getmatiere(uclasse,semestre);

                    call.enqueue(new Callback<List<matiere>>() {
                        @Override
                        public void onResponse(Call<List<matiere>> call, Response<List<matiere>> response) {

                            List<matiere> matList = response.body();


                            String[] mat = new String[matList.size()];
                            for (int i = 0; i < matList.size(); i++) {
                                mat[i] = matList.get(i).getNommatiere();
                            }

                            ArrayAdapter<String> adaptermat;
                            adaptermat = new ArrayAdapter<String>(resultat.this, android.R.layout.simple_list_item_1, mat);
                            matiere1.setAdapter(adaptermat);
                        }

                        @Override
                        public void onFailure(Call<List<matiere>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        Spinner test =(Spinner)findViewById(R.id.sptest);
        ArrayAdapter<String> adaptertest = new ArrayAdapter<String>(resultat.this,
                android.R.layout.simple_expandable_list_item_1,getResources().getStringArray(R.array.test));
        adaptertest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        test.setAdapter(adaptertest);

        final String var1 = (test.getSelectedItem()).toString();
        String lien;


        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mat=(matiere1.getSelectedItem().toString());

                RadioButton selectedRadioButton = (RadioButton) findViewById(rGroup.getCheckedRadioButtonId());
                String sem = selectedRadioButton.getText().toString();
               // Toast.makeText(getApplicationContext(),""+sem,Toast.LENGTH_LONG).show();




                IEtudiant apii = retrofit.create(IEtudiant.class);
                Call<resultatmatiere> call = apii.getresultat(uclasse,sem,mat);


                call.enqueue(new Callback<resultatmatiere>() {
                    @Override
                    public void onResponse(Call<resultatmatiere> call, Response<resultatmatiere> response) {
                        resultatmatiere rm=new resultatmatiere();
                        rm=response.body();


                       String lien="";
                        String varexamen=rm.getExamen().toString();
                        String vards1=rm.getDs1().toString();
                        String vards2=rm.getDs2().toString();
                        String vards3=rm.getDs3().toString();
                        String vartest=rm.getTest().toString();
                        String vartp=rm.getTp().toString();

                        if(var1.equals("Examen")){lien=varexamen;}
                        if(var1.equals("DS1")){lien=vards1;}
                        if(var1.equals("DS2")){lien=vards2;}
                        if(var1.equals("DS3")){lien=vards3;}
                        if(var1.equals("Test")){lien=vartest;}
                        if(var1.equals("TP")){lien=vartp;}


                        SharedPreferences sp = getSharedPreferences("lien", Context.MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("lien", lien);
                        Ed.apply();
                        startActivity(new Intent(resultat.this,resultat2.class));

                    }

                    @Override
                    public void onFailure(Call<resultatmatiere> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
   }


}
