package com.example.acer.finder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acer on 28.08.2017.
 */

public class InnerDetail implements Parcelable{

    String international_phone_number;
    String formatted_address;
    String rating;



    public InnerDetail(String formatted_address, String international_phone_number,String rating) {
        this.formatted_address = formatted_address;
        this.international_phone_number = international_phone_number;
        this.rating=rating;
    }

    protected InnerDetail(Parcel in) {
        international_phone_number = in.readString();
        formatted_address = in.readString();
        rating = in.readString();
    }

    public static final Creator<InnerDetail> CREATOR = new Creator<InnerDetail>() {
        @Override
        public InnerDetail createFromParcel(Parcel in) {
            return new InnerDetail(in);
        }

        @Override
        public InnerDetail[] newArray(int size) {
            return new InnerDetail[size];
        }
    };

    public String getFormatted_address() {
        return formatted_address;
    }

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(international_phone_number);
        dest.writeString(formatted_address);
        dest.writeString(rating);
    }
}
