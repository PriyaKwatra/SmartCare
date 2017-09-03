package com.example.acer.finder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acer on 27.08.2017.
 */

public class Results implements Parcelable {


    String icon;
    String id;
    String name;
    String place_id;
    Opening opening_hours;

    public Results(String icon, String id, String name, String place_id, Opening opening_hours) {
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
        this.opening_hours = opening_hours;
    }

    protected Results(Parcel in) {
        icon = in.readString();
        id = in.readString();
        name = in.readString();
        place_id = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public Opening getOpening_hours() {
        return opening_hours;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setOpening_hours(Opening opening_hours) {
        this.opening_hours = opening_hours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(place_id);
    }
}
