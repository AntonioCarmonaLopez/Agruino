package com.dam.agruino.model;

public class ValueLog extends ValueHistory{

    private String date;
    private String time;
    private long dateUnix;

    public ValueLog(ValueHistory valueHistory) {
        super(valueHistory);
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getDateUnix() {
        return dateUnix;
    }

    public void setDateUnix(long dateUnix) {
        this.dateUnix = dateUnix;
    }
}
