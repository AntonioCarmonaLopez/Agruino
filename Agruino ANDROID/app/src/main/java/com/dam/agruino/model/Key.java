package com.dam.agruino.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Key implements Parcelable {
    private String key;
    private int icon;

    public Key() {
        key = "";
        icon = 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    //parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeInt(icon);
    }
    protected Key(Parcel in) {
        key = in.readString();
        icon = in.readInt();
    }

    public static final Creator<Key> CREATOR = new Creator<Key>() {
        @Override
        public Key createFromParcel(Parcel in) {
            return new Key(in);
        }

        @Override
        public Key[] newArray(int size) {
            return new Key[size];
        }
    };
}
