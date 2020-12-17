package com.dam.agruino.model;

public class ValueHistory extends Value {

    private float valueHistory;

    public ValueHistory(Value value) {
        super(value.getKey(), value.getMeasure());
    }


    public float getValueHistory() {
        return valueHistory;
    }

    public void setValueHistory(float valueHistory) {
        this.valueHistory = valueHistory;
    }

}
