package com.dam.agruino.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.dam.agruino.R;
import com.dam.agruino.model.Value;
import com.dam.agruino.view.adapters.AdapterStats;

import java.util.ArrayList;

public class StatsFragment extends Fragment {

    private RecyclerView rvStats;
    private AdapterStats mAdapter;
    private ArrayList<Value> mKeys;
    private OnStatsFrag mListener;

    public interface OnStatsFrag{

        void onClickStats(AdapterStats adapterStats);
    }

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance(Bundle args) {
        StatsFragment fragment = new StatsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnStatsFrag) {
            mListener = (StatsFragment.OnStatsFrag) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStatFrag");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKeys = getArguments().getParcelableArrayList("keys");
            //init
            mAdapter = new AdapterStats();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        //findviewbyid
        rvStats = v.findViewById(R.id.rvStats);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //listener
        mAdapter.setOnClickListener(cv_onclick);
        // Init RecyclerView
        mAdapter.setValues(mKeys);
        mAdapter.notifyDataSetChanged();

        rvStats.setHasFixedSize(true);
        rvStats.setLayoutManager(new LinearLayoutManager(requireActivity()));
        for(int i=0;i<50;i++)
            rvStats.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        rvStats.setAdapter(mAdapter);
    }

    private SearchView.OnClickListener cv_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onClickStats(mAdapter);
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