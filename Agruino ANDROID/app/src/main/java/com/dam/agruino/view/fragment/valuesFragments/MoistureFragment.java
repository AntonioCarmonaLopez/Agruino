package com.dam.agruino.view.fragment.valuesFragments;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Objects;

public class MoistureFragment extends Fragment {

    private ImageView imSem;
    private TextView tvTittle,tvValues,tvMeasure;
    private Value value;
    private FirebaseFirestore db;
    private FirebaseFirestoreSettings settings;

    public MoistureFragment() {
        // Required empty public constructor
    }


    public static MoistureFragment newInstance(Bundle args) {
        MoistureFragment fragment = new MoistureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //init
        // instance that provide us a persistence unit
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
        }
        adquireValues();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moisture, container, false);

        //find view
        tvValues = v.findViewById(R.id.tvValues);
        tvTittle = v.findViewById(R.id.tvMoistureTittle);
        tvMeasure = v.findViewById(R.id.tvMeasure);
        imSem = v.findViewById(R.id.imSem);
        //init
        tvTittle.setText(value.getKey());
        tvMeasure.setText(value.getMeasure());
        //listeners

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

                    value.setValue(Float.parseFloat(Objects.toString(snapshot.get("moisture"))));

                    Log.d("agruino",Float.toString(value.getValue()));
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
        tvValues.setText(Float.toString(value.getValue()));
        if (value.getValue() < 20)
            imSem.setImageResource(R.drawable.sem_rojo);
        else if ((value.getValue() > 20) & (value.getValue() < 60))
            imSem.setImageResource(R.drawable.sem_ambar);
        else
            imSem.setImageResource(R.drawable.sem_verde);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        db = null;
        value = null;
        settings = null;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}