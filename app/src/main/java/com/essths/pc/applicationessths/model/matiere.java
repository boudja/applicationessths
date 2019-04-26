package com.essths.pc.applicationessths.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class matiere {

    @SerializedName("nommatiere")
    @Expose
    private String nommatiere;

    public String getNommatiere() {
        return nommatiere;
    }

    public void setNommatiere(String nommatiere) {
        this.nommatiere = nommatiere;
    }

}
