package com.essths.pc.applicationessths.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class emploi {

    @SerializedName("emploi")
    @Expose
    private String emploi;

    public String getEmploi() {
        return emploi;
    }

    public void setEmploi(String emploi) {
        this.emploi = emploi;
    }
}

