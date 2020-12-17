package com.dam.agruino.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Value implements Parcelable {
    private String key;
    private float value;
    private String measure;


    public Value(String key, String measure) {
        this.key = key;
        this.measure = measure;
    }

    public String getKey() {
        return key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setKey(String key) {
        this.key = key;
    }

//parcelable

    public Value(Parcel in) {
        key = in.readString();
        value = in.readByte();
        measure = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeFloat(value);
        dest.writeString(measure);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Value> CREATOR = new Creator<Value>() {
        @Override
        public Value createFromParcel(Parcel in) {
            return new Value(in);
        }

        @Override
        public Value[] newArray(int size) {
            return new Value[size];
        }
    };

}
