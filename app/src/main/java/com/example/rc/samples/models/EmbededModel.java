package com.example.rc.samples.models;

import java.io.Serializable;
import java.util.List;

public class EmbededModel implements Serializable {
    private List<BookModel> data;

    public List<BookModel> getData() {
        return data;
    }
}
