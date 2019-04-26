package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.essths.pc.applicationessths.Config.ConfigRetrofit;
import com.essths.pc.applicationessths.model.Etudiant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class changemdp extends AppCompatActivity {
    EditText ancienmdp,nvmdp,retapmdp;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changemdp);

        ancienmdp = (EditText) findViewById(R.id.ancien);
        nvmdp = (EditText) findViewById(R.id.nouveau);
        retapmdp = (EditText) findViewById(R.id.renouveau);




        Button btn = (Button) findViewById(R.id.confirmer);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String umdp = sharedPreferences.getString("mdp","dsm");
        final String ucin = sharedPreferences.getString("cin","ds");
        final String uinscri = sharedPreferences.getString("inscri","dsd");
        final String unom = sharedPreferences.getString("nom","dss");
        final String uprenom = sharedPreferences.getString("prenom", "kkj");




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String password = ancienmdp.getText().toString();
                final String newpw = nvmdp.getText().toString();
                final String retappw = retapmdp.getText().toString();




                if ( (password.equals(umdp)) && (newpw.equals(retappw)) )  {

                    Etudiant _etudiant = new Etudiant(Integer.parseInt(ucin),unom,uprenom,uinscri,newpw);
                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                    Call<Etudiant> call = etudiant.changermdp(_etudiant);
                    call.enqueue(new Callback<Etudiant>() {
                        @Override
                        public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {
                            Toast.makeText(changemdp.this, "Changement du mot de passe effectué" , Toast.LENGTH_LONG).show();
                            ancienmdp.setText("");
                            nvmdp.setText("");
                            retapmdp.setText("");


                            // startActivity(new Intent(changemdp.this, Fragmentop.class));

                        }

                        @Override
                        public void onFailure(Call<Etudiant> call, Throwable t) {
                            Toast.makeText(changemdp.this, "Erreur" +t.getMessage() , Toast.LENGTH_LONG).show();

                        }
                    });



                } else if (!password.equals(umdp)) {
                    Toast.makeText(changemdp.this, "Ancien mot de passe incorrect !", Toast.LENGTH_LONG).show();

                }
                else if (!newpw.equals(retappw)) {
                    Toast.makeText(changemdp.this, "Veuillez retaper votre nouveau mot de passe"  , Toast.LENGTH_LONG).show();

                } else if (password.equals("") && newpw.equals("") && retappw.equals("") ) {
                    Toast.makeText(changemdp.this, "Veuillez Saisir Tous Les Champs"  , Toast.LENGTH_LONG).show();

                }
            }
        });




    }
}
