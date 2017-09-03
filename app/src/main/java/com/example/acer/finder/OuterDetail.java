package com.example.acer.finder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acer on 28.08.2017.
 */

public class OuterDetail implements Parcelable {

    InnerDetail result;

    public OuterDetail(InnerDetail result) {
        this.result = result;
    }

    protected OuterDetail(Parcel in) {
    }

    public static final Creator<OuterDetail> CREATOR = new Creator<OuterDetail>() {
        @Override
        public OuterDetail createFromParcel(Parcel in) {
            return new OuterDetail(in);
        }

        @Override
        public OuterDetail[] newArray(int size) {
            return new OuterDetail[size];
        }
    };

    public InnerDetail getResult() {
        return result;
    }

    public void setResult(InnerDetail results) {
        this.result = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
