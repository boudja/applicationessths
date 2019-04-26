package com.essths.pc.applicationessths.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class calendrier {

    @SerializedName("classe")
    @Expose
    private String classe;
    @SerializedName("semestre")
    @Expose
    private String semestre;
    @SerializedName("ds")
    @Expose
    private String ds;
    @SerializedName("examen")
    @Expose
    private String examen;

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }

}
