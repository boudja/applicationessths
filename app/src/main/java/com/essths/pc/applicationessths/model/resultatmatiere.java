package com.essths.pc.applicationessths.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class resultatmatiere {

    @SerializedName("classe")
    @Expose
    private String classe;
    @SerializedName("nommatiere")
    @Expose
    private String nommatiere;
    @SerializedName("semestre")
    @Expose
    private String semestre;
    @SerializedName("test")
    @Expose
    private String test;
    @SerializedName("tp")
    @Expose
    private String tp;
    @SerializedName("ds1")
    @Expose
    private String ds1;
    @SerializedName("ds2")
    @Expose
    private String ds2;
    @SerializedName("ds3")
    @Expose
    private String ds3;
    @SerializedName("examen")
    @Expose
    private String examen;

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNommatiere() {
        return nommatiere;
    }

    public void setNommatiere(String nommatiere) {
        this.nommatiere = nommatiere;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getDs1() {
        return ds1;
    }

    public void setDs1(String ds1) {
        this.ds1 = ds1;
    }

    public String getDs2() {
        return ds2;
    }

    public void setDs2(String ds2) {
        this.ds2 = ds2;
    }

    public String getDs3() {
        return ds3;
    }

    public void setDs3(String ds3) {
        this.ds3 = ds3;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }

}
