package com.example.acer.finder;

import java.util.ArrayList;

/**
 * Created by acer on 27.08.2017.
 */

public class OuterClass {

    ArrayList<Results> results ;

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public OuterClass(ArrayList<Results> results) {
        this.results = results;
    }

    public ArrayList<Results> getResults() {
        return results;
    }
}
