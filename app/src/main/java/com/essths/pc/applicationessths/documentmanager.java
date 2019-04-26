package com.essths.pc.applicationessths;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.essths.pc.applicationessths.Config.ConfigRetrofit;
import com.essths.pc.applicationessths.model.document;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;

public class documentmanager extends AppCompatActivity {
    int mycount;

    String[]actions={"مطلب مناقلة بين الأفواج(خاص بطلبة الاجازات)",
           "مطلب مناقلة بين الأفواج(خاص بطلبة المرحلة التحضيرية)",
           "مطلب للحصول على شهادة مغادرة والدفتر الصحي",
           "مطلب للحصول على شهادة حضور",
           "مطلب للحصول على نظير من وثيقة إدارية",
           "مطلب إصلاح وثيقة إدارية",
           "مطلب تأجيل ترسيم لأسباب شخصية",
           "مطلب تأجيل ترسيم لأسباب صحية",
           "مطلب تسجيل استثنائي",
           "مطلب في التثبت من العدد المسند",

           "مطلب للحصول على سيرة جامعية",
           "مطلب للاطلاع على ورقة الامتحان",
           "مطلب استخراج كشف أعداد بالأرصدة",
           "مطلب استخراج كشف أعداد بالرتبة",
           "مطلب تثمين الوحدات",
           "مطلب للاكتساب النهائي للأرصدة",
           "مطلب استخراج كشف أعداد للسداسي الأول",
           "مطلب بعث نادي طلابي",
           "مطلب تغيير اختصاص"

    };
    ListView mylv;
    ImageView myimg;
    NotificationBadge mybadge;


    //boolean disp=true;
    //int varpos;

    ConfigRetrofit config = new ConfigRetrofit();
    Retrofit retrofit = config.getConfig();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentmanager);



        mylv=(ListView)findViewById(R.id.lvdoc);
        myimg=(ImageView)findViewById(R.id.icon);
        mybadge=(NotificationBadge) findViewById(R.id.badge);




        CustomAdapter1 ca=new CustomAdapter1();
        mylv.setAdapter(ca);


        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String ucin = sharedPreferences.getString("cin","ds");

        ////
        IEtudiant etudiant = retrofit.create(IEtudiant.class);
        Call<List<document>> call = etudiant.getdocument(ucin,"disponible");
        call.enqueue(new Callback<List<document>>() {
            @Override
            public void onResponse(Call<List<document>> call, Response<List<document>> response) {

                List<document> documentsList = response.body();
                String[] documents = new String[documentsList.size()];
                for (int i = 0; i < documentsList.size(); i++) {
                    documents[i] = documentsList.get(i).getDocument();

                }mycount=documentsList.size();
                mybadge.setNumber(mycount);
            }

            @Override
            public void onFailure(Call<List<document>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();


            }

        });

        myimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(documentmanager.this, documentspret.class));

            }
        });


        mylv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                if(i==0){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)

                            //en cliquant sur oui
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب مناقلة بين الأفواج(خاص بطلبة الاجازات)";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"disponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            //en cliquant sur non
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                  dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب مناقلة بين الأفواج(خاص بطلبة الاجازات)");
                    alert.show();
                }
                if(i==1){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب مناقلة بين الأفواج(خاص بطلبة المرحلة التحضيرية)";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب مناقلة بين الأفواج(خاص بطلبة المرحلة التحضيرية)");
                    alert.show();
                }
                if(i==2){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب للحصول على شهادة مغادرة والدفتر الصحي";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب للحصول على شهادة مغادرة والدفتر الصحي");
                    alert.show();
                }
                if(i==3){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب للحصول على شهادة حضور";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب للحصول على شهادة حضور");
                    alert.show();
                }
                if(i==4){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب للحصول على نظير من وثيقة إدارية";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب للحصول على نظير من وثيقة إدارية");
                    alert.show();
                }
                if(i==5){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب إصلاح وثيقة إدارية";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب إصلاح وثيقة إدارية");
                    alert.show();
                }
                if(i==6){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب تأجيل ترسيم لأسباب شخصية";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب تأجيل ترسيم لأسباب شخصية");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب تأجيل ترسيم لأسباب شخصية",Toast.LENGTH_SHORT).show();
                }
                if(i==7){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب تأجيل ترسيم لأسباب صحية";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب تأجيل ترسيم لأسباب صحية");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب تأجيل ترسيم لأسباب صحية",Toast.LENGTH_SHORT).show();
                }
                if(i==8){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب تسجيل استثنائي";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب تسجيل استثنائي");
                    alert.show();
                    //Toast.makeText(documentmanager.this,"مطلب تسجيل استثنائي",Toast.LENGTH_SHORT).show();
                }
                if(i==9){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب في التثبت من العدد المسند";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب في التثبت من العدد المسند");
                    alert.show();
                    //Toast.makeText(documentmanager.this,"مطلب في التثبت من العدد المسند",Toast.LENGTH_SHORT).show();
                }
                if(i==10){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب للحصول على سيرة جامعية";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب للحصول على سيرة جامعية");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب للحصول على سيرة جامعية",Toast.LENGTH_SHORT).show();
                }
                if(i==11){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب للاطلاع على ورقة الامتحان";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب للاطلاع على ورقة الامتحان");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب للاطلاع على ورقة الامتحان",Toast.LENGTH_SHORT).show();
                }
                if(i==12){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب استخراج كشف أعداد بالأرصدة";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب استخراج كشف أعداد بالأرصدة");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب استخراج كشف أعداد بالأرصدة",Toast.LENGTH_SHORT).show();
                }
                if(i==13){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب استخراج كشف أعداد بالرتبة";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب استخراج كشف أعداد بالرتبة");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب استخراج كشف أعداد بالرتبة",Toast.LENGTH_SHORT).show();
                }
                if(i==14){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب تثمين الوحدات";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب تثمين الوحدات");
                    alert.show();
                    //Toast.makeText(documentmanager.this,"مطلب تثمين الوحدات",Toast.LENGTH_SHORT).show();
                }
                if(i==15){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب للاكتساب النهائي للأرصدة";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب للاكتساب النهائي للأرصدة");
                    alert.show();
                  //  Toast.makeText(documentmanager.this,"مطلب للاكتساب النهائي للأرصدة",Toast.LENGTH_SHORT).show();
                }
                if(i==16){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب استخراج كشف أعداد للسداسي الأول";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب استخراج كشف أعداد للسداسي الأول");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب استخراج كشف أعداد للسداسي الأول",Toast.LENGTH_SHORT).show();
                }
                if(i==17){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب بعث نادي طلابي";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب بعث نادي طلابي");
                    alert.show();
                    //Toast.makeText(documentmanager.this,"مطلب بعث نادي طلابي",Toast.LENGTH_SHORT).show();
                }
                if(i==18){
                    AlertDialog.Builder ald=new AlertDialog.Builder(documentmanager.this);
                    ald.setMessage("Confirmer votre Demande ?").setCancelable(false)
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String nomdoc="مطلب تغيير اختصاص";
                                    Toast.makeText(documentmanager.this,nomdoc,Toast.LENGTH_SHORT).show();
                                    IEtudiant etudiant = retrofit.create(IEtudiant.class);
                                    Call<String> call = etudiant.insertdoc(ucin, nomdoc,"nondisponible");
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            Toast.makeText(getApplicationContext(),"document ajoutée avec succes",Toast.LENGTH_LONG).show();


                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert=ald.create();
                    alert.setTitle("مطلب تغيير اختصاص");
                    alert.show();
                   // Toast.makeText(documentmanager.this,"مطلب تغيير اختصاص",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class CustomAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            return actions.length;
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
            View view=getLayoutInflater().inflate(R.layout.doccustomlay,null);
            TextView mytxt = (TextView)view.findViewById(R.id.tvdoc);
            mytxt.setText(actions[position]);
            return view;
        }
    }
}
