package com.example.rc.samples.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseBookModel implements Serializable {

    @SerializedName("_embedded")
    private EmbededModel embedded;

    public EmbededModel getEmbedded() {
        return embedded;
    }
}
