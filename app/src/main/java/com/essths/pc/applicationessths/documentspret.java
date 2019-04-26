package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.essths.pc.applicationessths.Config.ConfigRetrofit;
import com.essths.pc.applicationessths.model.document;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class documentspret extends AppCompatActivity {
ListView mylv;
FloatingActionButton fab;
    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentspret);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String ucin = sharedPreferences.getString("cin","ds");


        mylv=(ListView)findViewById(R.id.lvdocpret);
        fab=(FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(documentspret.this, documentmanager.class));
            }
        });
        //remplir la liste
        IEtudiant etudiant = retrofit.create(IEtudiant.class);
        Call<List<document>> call = etudiant.getdocument(ucin,"disponible");
        call.enqueue(new Callback<List<document>>() {
            @Override
            public void onResponse(Call<List<document>> call, Response<List<document>> response) {

                List<document> documentsList = response.body();
                String[] documents = new String[documentsList.size()];
                for (int i = 0; i < documentsList.size(); i++) {
                    documents[i] = documentsList.get(i).getDocument();
                }
                ArrayAdapter<String> adapterplace;
                adapterplace= new ArrayAdapter<String>(documentspret.this, android.R.layout.simple_list_item_1, documents);
                mylv.setAdapter(adapterplace);
            }

            @Override
            public void onFailure(Call<List<document>> call, Throwable t) {

            }
        });



        mylv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String var = mylv.getItemAtPosition(position).toString();

                AlertDialog.Builder ald=new AlertDialog.Builder(documentspret.this);
                ald.setMessage("Supprimer cette Demande ?").setCancelable(false)
                        //si on click sur oui
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(documentspret.this,var,Toast.LENGTH_SHORT).show();



                                   IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                   Call<String> calll = etudiant.supprimer(ucin,var,"disponible");



                                  calll.enqueue(new Callback<String>() {
                                  @Override
                                  public void onResponse(Call<String> call, Response<String> response) {
                                   Toast.makeText(getApplicationContext(),"Demande supprimée avec succés",Toast.LENGTH_LONG).show();

                                   }

                                  @Override
                                   public void onFailure(Call<String> call, Throwable t) {

                                  }
                                  });

                                  //mettre a jour la liste aprés suppression
                                IEtudiant etudiant1 = retrofit.create(IEtudiant.class);
                                Call<List<document>> call1 = etudiant1.getdocument(ucin,"disponible");
                                call1.enqueue(new Callback<List<document>>() {
                                    @Override
                                    public void onResponse(Call<List<document>> call, Response<List<document>> response) {

                                        List<document> documentsList = response.body();
                                        String[] documents = new String[documentsList.size()];
                                        for (int i = 0; i < documentsList.size(); i++) {
                                            documents[i] = documentsList.get(i).getDocument();
                                        }
                                        ArrayAdapter<String> adapterplace;
                                        adapterplace= new ArrayAdapter<String>(documentspret.this, android.R.layout.simple_list_item_1, documents);
                                        mylv.setAdapter(adapterplace);
                                    }

                                    @Override
                                    public void onFailure(Call<List<document>> call, Throwable t) {

                                    }
                                });
                            }
                        })



                        //si on click sur non
                        .setNegativeButton("non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert=ald.create();
                alert.setTitle("Les documents que vous avez demandés sont prêts à être retirés");
                alert.show();



            }
        });

    }


}
