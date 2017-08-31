package com.example.acer.finder;

/**
 * Created by acer on 28.08.2017.
 */

public class OuterDetail {

    InnerDetail result;

    public OuterDetail(InnerDetail result) {
        this.result = result;
    }

    public InnerDetail getResult() {
        return result;
    }

    public void setResult(InnerDetail results) {
        this.result = results;
    }
}
