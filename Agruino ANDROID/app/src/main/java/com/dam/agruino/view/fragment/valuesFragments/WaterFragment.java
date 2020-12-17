package com.dam.agruino.view.fragment.valuesFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.dam.agruino.R;
import com.dam.agruino.view.adapters.AdapterWaterKeys;

import java.util.ArrayList;


public class WaterFragment extends Fragment {

    private RecyclerView rvWaterKeys;
    private AdapterWaterKeys mAdapter;
    private ArrayList <String> waterKeys;
    private OnWaterFragment mListener;

    public interface OnWaterFragment{
        void OnclickWaterFragment(AdapterWaterKeys mAdapter);
    }

    public WaterFragment() {
        // Required empty public constructor
    }

    public static WaterFragment newInstance(Bundle args) {
        WaterFragment fragment = new WaterFragment();
        if(args != null)
            fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnWaterFragment) {
            mListener = (OnWaterFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnWaterFragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            waterKeys = getArguments().getStringArrayList("waterKeys");
            Log.d("agruino",waterKeys.get(1));
            //init
            mAdapter = new AdapterWaterKeys();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_water, container, false);
        //findviewbyid
        rvWaterKeys = v.findViewById(R.id.rvWaterKeys);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //listener
        mAdapter.setOnClickListener(cv_onclick);
        // Init RecyclerView
        mAdapter.setKeys(waterKeys);
        mAdapter.notifyDataSetChanged();

        rvWaterKeys.setHasFixedSize(true);
        rvWaterKeys.setLayoutManager(new LinearLayoutManager(requireActivity()));
        for(int i=0;i<10;i++)
            rvWaterKeys.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        rvWaterKeys.setAdapter(mAdapter);
    }

    private SearchView.OnClickListener cv_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.OnclickWaterFragment(mAdapter);
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}