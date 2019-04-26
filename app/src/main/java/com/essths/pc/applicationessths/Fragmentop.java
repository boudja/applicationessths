package com.essths.pc.applicationessths;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmentop extends Fragment {

ListView mylv;
TextView nom,pre;
//private frangmentdoc fdoc;
//private FrameLayout mainf;
int[]image={R.drawable.resultat,
            R.drawable.calendar,
            R.drawable.document,
            R.drawable.mdp,
            R.drawable.moyenne,
            R.drawable.emploi};
String[]actions={"Les resultats","Calendrier d'examens","Documents","Changer mot de passe","Moyenne","Emploi du temps"};


    public Fragmentop() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_fragmentop, container, false);
        mylv = (ListView)v.findViewById(R.id.lvop);
        pre = (TextView) v.findViewById(R.id.prenom);
        nom = (TextView) v.findViewById(R.id.nom);
        CustomAdapter ca=new CustomAdapter();
        mylv.setAdapter(ca);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        //SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String unom = sharedPreferences.getString("nom","dss");
        final String uprenom = sharedPreferences.getString("prenom", "kkj");

        nom.setText(unom);
        pre.setText(uprenom);

       // mainf=(FrameLayout)v.findViewById(R.id.mainframe);
       // fdoc =new frangmentdoc();

        mylv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               if(position==0){
                   Intent myintent=new Intent(view.getContext(),resultat.class);
                   startActivityForResult(myintent,0);
               }
                if(position==1){
                    Intent myintent=new Intent(view.getContext(),calendrierexamens.class);
                    startActivityForResult(myintent,0);
                }
                if(position==2){//documents
                    /*Uri uri = Uri.parse("http://www.essths.rnu.tn/FR/formulaire.html");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);*/
                    /*android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mainframe,fdoc);
                    fragmentTransaction.commit();*/
                    Intent myintent=new Intent(view.getContext(),documentmanager.class);
                    startActivityForResult(myintent,0);

                }
                if(position==3){
                    Intent myintent=new Intent(view.getContext(),changemdp.class);
                    startActivityForResult(myintent,0);
                }
                if(position==4){
                    Intent myintent=new Intent(view.getContext(),moyenne.class);
                    startActivityForResult(myintent,0);
                }
                if(position==5){
                    Intent myintent=new Intent(view.getContext(),emploidutemps.class);
                    startActivityForResult(myintent,0);
                }

            }
        });

        return v;
    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.opcustomlay,null);
            ImageView myimg = (ImageView)view.findViewById(R.id.imageView2);
            TextView mytxt = (TextView)view.findViewById(R.id.textViewop);
            myimg.setImageResource(image[position]);
            mytxt.setText(actions[position]);

            return view;
        }
    }

}
