package com.dam.agruino.controler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.dam.agruino.model.ValueLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Tools {

    public static boolean isNumeric(final String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static int calcDayOfTear(){
        Calendar calendar = Calendar.getInstance();
        int num_dia = calendar.get(Calendar.DAY_OF_YEAR);
        int d = num_dia;
        return d;
    }

    public static String today(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String today=String.format("%02d/%02d/%4d", day, month + 1, year);
        return  today;
    }
}
