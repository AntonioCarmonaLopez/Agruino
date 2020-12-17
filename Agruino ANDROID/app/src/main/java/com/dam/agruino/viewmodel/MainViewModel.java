package com.dam.agruino.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam.agruino.model.Filter;
import com.dam.agruino.model.ValueHistory;
import com.dam.agruino.model.ValueLog;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Filter> mFilter;
    private MutableLiveData<String> mDate;
    private ValueLog valueLog;
    private String date;
    private String key;
    private List<ValueLog> values;

    public MainViewModel() {
        mFilter = null;
        valueLog = null;
    }

    public MutableLiveData<Filter> getmFilter() {
        if (mFilter == null) {
            mFilter = new MutableLiveData<>();
        }
        return mFilter;
    }

    public void setmFilter(Filter mFilter) {

        this.mFilter.setValue(mFilter);
    }

    public MutableLiveData<String> getmDate() {
        if(mDate == null)
            mDate = new MutableLiveData<>();
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate.setValue(mDate);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ValueLog> getValues() {
        return values;
    }

    public void setValues(List<ValueLog> values) {
        this.values = values;
    }

    public ValueLog getValueLog() {
        return valueLog;
    }

    public void setValueLog(ValueLog valueLog) {
        this.valueLog = valueLog;
    }
}
