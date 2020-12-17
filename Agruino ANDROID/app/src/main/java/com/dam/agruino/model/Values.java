package com.dam.agruino.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Values implements Parcelable {
    float moisture;
    float conductivity;
    float turbidity;
    float ph;
    float temp;

    public Values() {
        moisture = 0;
        conductivity = 0;
        turbidity = 0;
        ph = 0;
        temp = 0;
    }

    public float getMoisture() {
        return moisture;
    }

    public void setMoisture(float moisture) {
        this.moisture = moisture;
    }

    public float getConductivity() {
        return conductivity;
    }

    public void setConductivity(float conductivity) {
        this.conductivity = conductivity;
    }

    public float getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(float turbidity) {
        this.turbidity = turbidity;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    //parcelable
    public Values(Parcel in) {
        moisture = in.readFloat();
        conductivity = in.readFloat();
        turbidity = in.readFloat();
        ph = in.readFloat();
        temp = in.readFloat();
    }

    public static final Creator<Values> CREATOR = new Creator<Values>() {
        @Override
        public Values createFromParcel(Parcel in) {
            return new Values(in);
        }

        @Override
        public Values[] newArray(int size) {
            return new Values[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(moisture);
        dest.writeFloat(conductivity);
        dest.writeFloat(turbidity);
        dest.writeFloat(ph);
        dest.writeFloat(temp);
    }
}
