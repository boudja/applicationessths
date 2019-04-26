package com.essths.pc.applicationessths.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class document {

    @SerializedName("document")
    @Expose
    private String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

}