package com.essths.pc.applicationessths;

/**
 * Created by pc on 12/04/2018.
 */
import com.essths.pc.applicationessths.model.Etudiant;
import com.essths.pc.applicationessths.model.calendrier;
import com.essths.pc.applicationessths.model.document;
import com.essths.pc.applicationessths.model.emploi;
import com.essths.pc.applicationessths.model.matiere;
import com.essths.pc.applicationessths.model.moyennemod;
import com.essths.pc.applicationessths.model.resultatmatiere;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 03/04/2018.
 */

public interface IEtudiant {




    //deposer une demande de documents administratif
    @GET("insertdoc")
    Call<String> insertdoc(@Query("cin")String cin,
                           @Query("document")String document,
                           @Query("etat")String etat);





    //pour le login
    @GET("login/{cin}/{password}")
    Call<Etudiant> login (@Path("cin") String cin , @Path("password") String password);




    //changer le mot de passe
    @POST("update/changermdp")
    Call<Etudiant> changermdp(@Body Etudiant etudiant);



    //pour remplir listview en documentspret en documents qui sont pret a etre retirer
    @GET("documents")
    Call<List<document>> getdocument(@Query("cin")String cin, @Query("etat")String etat);



    //pour supprimer les document prés a etre retirer
    @GET("supprimer")
    Call<String> supprimer(@Query("cin")String cin,
                      @Query("document")String document,
                      @Query("etat")String etat);



    @GET("matiere")
    Call<List<matiere>> getmatiere(@Query("classe")String classe,
                                   @Query("semestre")String semestre);






   /* @GET("matiereres")
    Call<resultatmatiere> getresultat(@Query("classe")String classe,
                                      @Query("semestre")String semestre,
                                      @Query("nommatiere")String nommatiere );*/



   @GET("matiereres")
   Call<resultatmatiere> getresultat(@Query("classe")String classe,
                                     @Query("semestre")String semestre,
                                     @Query("nommatiere")String nommatiere );





    @GET("emploi")
    Call<emploi> getemploi(@Query("classe")String classe,
                           @Query("semestre")String semestre);

    @GET("moyenne")
    Call<moyennemod> getmoyenne(@Query("classe")String classe,
                                @Query("semestre")String semestre);



    @GET("calendrier")
    Call<calendrier> getcalendrier(@Query("classe")String classe,
                                 @Query("semestre")String semestre);
















}
