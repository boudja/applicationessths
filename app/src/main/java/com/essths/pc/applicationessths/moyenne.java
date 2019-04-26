package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.essths.pc.applicationessths.model.emploi;
import com.essths.pc.applicationessths.model.matiere;
import com.essths.pc.applicationessths.model.moyennemod;
import com.essths.pc.applicationessths.model.resultatmatiere;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class moyenne extends AppCompatActivity {
    Button btnres;
    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moyenne);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String uclasse = sharedPreferences.getString("classe", "erreur");
        Toast.makeText(getApplicationContext(),"uclasse"+uclasse,Toast.LENGTH_SHORT).show();


        final RadioGroup rGroup = (RadioGroup)findViewById(R.id.semestre);
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
        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                RadioButton selectedRadioButton = (RadioButton) findViewById(rGroup.getCheckedRadioButtonId());
                String sem = selectedRadioButton.getText().toString();
                Toast.makeText(getApplicationContext(),""+sem,Toast.LENGTH_LONG).show();

                IEtudiant apii = retrofit.create(IEtudiant.class);
                Call<moyennemod> call = apii.getmoyenne(uclasse,sem);
                call.enqueue(new Callback<moyennemod>() {
                    @Override
                    public void onResponse(Call<moyennemod> call, Response<moyennemod> response) {
                        Toast.makeText(getApplicationContext(),"resultat"+response.body(),Toast.LENGTH_LONG).show();
                        moyennemod lieninter=response.body();
                        //String lien=lieninter.toString();
                        String lien=lieninter.getMoyenne();
                        SharedPreferences sp = getSharedPreferences("lienmoy", Context.MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("lien", lien);
                        Ed.apply();
                        startActivity(new Intent(moyenne.this,moyenne2.class));

                    }

                    @Override
                    public void onFailure(Call<moyennemod> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();


                    }
                });


            }
        });
    }
}
