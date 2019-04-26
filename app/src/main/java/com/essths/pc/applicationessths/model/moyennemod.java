package com.essths.pc.applicationessths.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class moyennemod {

    @SerializedName("moyenne")
    @Expose
    private String moyenne;

    public String getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(String moyenne) {
        this.moyenne = moyenne;
    }

}
