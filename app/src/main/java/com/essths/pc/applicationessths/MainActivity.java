package com.essths.pc.applicationessths;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
private BottomNavigationView nav;
private FrameLayout mainf;
private Fragmentacc fa;
private Fragmentnot fn;
private Fragmentop fo;

private android.support.v7.widget.Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mainf=(FrameLayout) findViewById(R.id.mainframe);
        nav=(BottomNavigationView) findViewById(R.id.mainnav);
        fa=new Fragmentacc();
        fn=new Fragmentnot();
        fo=new Fragmentop();

         mytoolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.mytoolbar);

         setSupportActionBar(mytoolbar);



        //pour affcher le fragment acceil dés l'accés au compte
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe,fa);
        fragmentTransaction.commit();

       //affichage du fragment selon le bouton qu'on a cliqué
       nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.actualites:
                        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.mainframe,fa);
                        fragmentTransaction.commit();

                         return true ;
                    case R.id.notification:
                        android.support.v4.app.FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.mainframe,fn);
                        fragmentTransaction1.commit();
                         return true;
                    case R.id.operation:
                        android.support.v4.app.FragmentTransaction fragmentTransaction2=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.mainframe,fo);
                        fragmentTransaction2.commit();
                        return true;
                    default:
                        return false;

                }
            }
        });


    }


    //pour afficher le menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi= getMenuInflater();
        mi.inflate(R.menu.mon_menu,menu);
        return true ;
    }


    //code du bouton logout
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.logout)
        {
            Toast.makeText(MainActivity.this,"logout",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

