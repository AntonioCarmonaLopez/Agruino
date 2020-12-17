package com.dam.agruino.view.fragment.valuesFragments.waterValues;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dam.agruino.R;
import com.dam.agruino.controler.Tools;
import com.dam.agruino.model.Value;
import com.dam.agruino.model.Values;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Objects;

public class WaterValuesFragment extends Fragment {

    private TextView tvTittle,tvValue,tvMeasure;
    private ImageView imSemWater;
    private Value value;
    private FirebaseFirestore db;
    private FirebaseFirestoreSettings settings;
    private String tittle;

    public WaterValuesFragment() {
        // Required empty public constructor
    }

    public static WaterValuesFragment newInstance(Bundle args) {
        WaterValuesFragment fragment = new WaterValuesFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //init
        // instance for configure chache size of a persistence unit
        // IOS & android devices, the persistence come activated by default
        settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();


        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            value = getArguments().getParcelable("value");
            if(value.getKey().equalsIgnoreCase("temperature")) {
                value.setKey("temp");
                tittle = "Temperature";
            }
            tittle ="null";
            Log.d("agruino", value.getKey());
        }
        if(!Tools.isNumeric(Float.toString(value.getValue())))
            value.getMeasure();
        adquireValues();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_water_values, container, false);
        //findview
        tvTittle = v.findViewById(R.id.tvTittle);
        tvValue = v.findViewById(R.id.tvWaterValue);
        tvMeasure = v.findViewById(R.id.tvWaterMeasure);
        imSemWater = v.findViewById(R.id.imSemWater);
        //init
        if(tittle.equals("Temperature"))
            tvTittle.setText(tittle);
        else
            tvTittle.setText(value.getKey());
        tvMeasure.setText(value.getMeasure());
        return v;
    }

    public void adquireValues() {
        final DocumentReference docRef = db.collection("values").document("YC0HJwj1qynXC12LVpAV");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("agruino", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("agruino", "Current data: " + snapshot.getData());
                    value.setValue(Float.parseFloat(Objects.toString(snapshot.get(value.getKey().toLowerCase()))));
                    if(!Tools.isNumeric(Float.toString(value.getValue())))
                        value.setValue(0);
                    updateRV();
                } else {
                    Log.d("agruino", "Current data: null");
                }
            }
        });

    }

    private void updateRV() {
        tvTittle.setText(value.getKey());

        if (value.getKey().equalsIgnoreCase("turbidity")) {
            if (Float.parseFloat(Objects.toString(value.getValue())) < 1)
                imSemWater.setImageResource(R.drawable.sem_verde);
            else if (Float.parseFloat(Objects.toString(value.getValue())) > 1 && Float.parseFloat(Objects.toString(value.getValue())) < 3)
                imSemWater.setImageResource(R.drawable.sem_ambar);
            else
                imSemWater.setImageResource(R.drawable.sem_rojo);
        } else if (value.getKey().equalsIgnoreCase("conductivity")) {
            if (Float.parseFloat(Objects.toString(value.getValue())) < 1)
                imSemWater.setImageResource(R.drawable.sem_ambar);
            else if (Float.parseFloat(Objects.toString(value.getValue())) > 1 && Float.parseFloat(Objects.toString(value.getValue())) < 2)
                imSemWater.setImageResource(R.drawable.sem_verde);
            else
                imSemWater.setImageResource(R.drawable.sem_rojo);
        } else if (value.getKey().equalsIgnoreCase("ph")) {
            if (Float.parseFloat(Objects.toString(value.getValue())) < 5.5) {
                tvMeasure.setText("Basic water");
                imSemWater.setImageResource(R.drawable.sem_rojo);
            } else if (Float.parseFloat(Objects.toString(value.getValue())) > 5.5 && Float.parseFloat(Objects.toString(value.getValue())) <= 7) {
                tvMeasure.setText("Rigth ph");
                imSemWater.setImageResource(R.drawable.sem_verde);
            } else {
                tvMeasure.setText("Acid water");
                imSemWater.setImageResource(R.drawable.sem_rojo);
            }
        } else {
            if (Float.parseFloat(Objects.toString(value.getValue())) < 0)
                imSemWater.setImageResource(R.drawable.sem_rojo);
            else if (Float.parseFloat(Objects.toString(value.getValue())) > 0 && Float.parseFloat(Objects.toString(value.getValue())) < 10)
                imSemWater.setImageResource(R.drawable.sem_ambar);
            else if (Float.parseFloat(Objects.toString(value.getValue())) > 10 && Float.parseFloat(Objects.toString(value.getValue())) < 20)
                imSemWater.setImageResource(R.drawable.sem_verde);
            else
                imSemWater.setImageResource(R.drawable.sem_rojo);
        }
        tvValue.setText(Objects.toString(value.getValue()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        db = null;
        settings = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}