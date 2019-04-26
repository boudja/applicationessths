package com.essths.pc.applicationessths.model;

/**
 * Created by pc on 12/04/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Etudiant {
    public Etudiant(Integer cin, String nom, String prenom, String numInscri, String mdp) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.numInscri = numInscri;
        this.mdp = mdp;
    }

    @SerializedName("cin")
    @Expose
    private Integer cin;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("prenom")
    @Expose
    private String prenom;
    @SerializedName("num_inscri")
    @Expose
    private String numInscri;
    @SerializedName("mdp")
    @Expose
    private String mdp;
    @SerializedName("classe")
    @Expose
    private String classe;

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumInscri() {
        return numInscri;
    }

    public void setNumInscri(String numInscri) {
        this.numInscri = numInscri;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String mdp) {
        this.classe = classe;
    }

}