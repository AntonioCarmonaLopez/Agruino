package com.dam.agruino.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dam.agruino.controler.Tools;

public class Filter implements Parcelable {
    private String date;

    public Filter() {
        date = Tools.today();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    //parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
    }

    protected Filter(Parcel in) {
        date = in.readString();
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };
}
