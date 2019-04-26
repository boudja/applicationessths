package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.essths.pc.applicationessths.Config.ConfigRetrofit;
import com.essths.pc.applicationessths.model.Etudiant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Main2Activity extends AppCompatActivity {

    Button connectionbtn;
    EditText login, password;
    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        connectionbtn =(Button) findViewById(R.id.cnxbtn);
        login = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText3);


        //code du bouton connecter
        connectionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Login = login.getText().toString();
                String pw = password.getText().toString();

                //si les 2 champs ne sont pas vides
                if (!Login.equals("") && !pw.equals("")) {

                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                    Call<Etudiant> call = etudiant.login(Login, pw);

                    call.enqueue(new Callback<Etudiant>() {
                        @Override
                        public void onResponse(Call<Etudiant> call, Response<Etudiant> response) {

                            //si on trouve le nom dans la base de données(compte existant)
                            if(response.body().getNom() != null) {

                                //pour recupérer les cordonnés de l'utilisateur
                                String nom = response.body().getNom().toString();
                                String prenom = response.body().getPrenom().toString();
                                String mdp = response.body().getMdp().toString();
                                String cin = response.body().getCin().toString();
                                String inscri = response.body().getNumInscri().toString();
                                String classe=response.body().getClasse().toString();

                                //pour afficher Salut+nom de l'utilisateur du compte
                                Toast.makeText(Main2Activity.this, " Salut : " + prenom, Toast.LENGTH_SHORT).show();

                                //pour mettre les valeurs des variables accessible dans d'autre activités
                                SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("nom", nom);
                                Ed.putString("prenom", prenom);
                                Ed.putString("mdp", mdp);
                                Ed.putString("cin", cin);
                                Ed.putString("inscri", inscri);
                                Ed.putString("classe", classe);
                                Ed.apply();

                                //ouvrir la prochaine activité
                                startActivity(new Intent(Main2Activity.this, MainActivity.class));

                                //si l'identifiant ou le mot de passe sont incorrect ou le compte est inexistant
                            }
                            else
                            {
                                Toast.makeText(Main2Activity.this, " Identifiant ou Mot de Passe Incorrect ! ", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Etudiant> call, Throwable t) {
                            Toast.makeText(Main2Activity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });

                 //si les 2 champs sont vides
                }  else
                {
                    Toast.makeText(Main2Activity.this, " Veuillez saisir votre identifiant et votre mot de passe ", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }






}
