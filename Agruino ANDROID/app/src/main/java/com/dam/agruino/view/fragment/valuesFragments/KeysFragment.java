package com.dam.agruino.view.fragment.valuesFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.dam.agruino.R;
import com.dam.agruino.model.Key;
import com.dam.agruino.view.adapters.AdapterKeys;

import java.util.ArrayList;
import java.util.function.Consumer;

public class KeysFragment extends Fragment {

    private RecyclerView rvKeys;
    private AdapterKeys mAdapter;
    private ArrayList<Key> mKeys;

    private KeyFragInterface mListener;

    public interface KeyFragInterface {

        void onClickFrag(AdapterKeys adapterKeys);

    }

    public KeysFragment() {
        // Required empty public constructor
    }

    public static KeysFragment newInstance(Bundle args) {
        KeysFragment fragment = new KeysFragment();
        if (args != null) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof KeyFragInterface) {
            mListener = (KeyFragInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement KeyFragInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKeys = getArguments().getParcelableArrayList("keys");
            Log.d("agruino","key-frag"+mKeys.get(1).getKey());
            //init
            mAdapter = new AdapterKeys();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_keys, container, false);
        //findbyid
        rvKeys = v.findViewById(R.id.rvKeys);
        //init


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //listener
        mAdapter.setOnClickListener(cv_onclick);
        // Init RecyclerView
        mAdapter.setKeys(mKeys);

        mAdapter.notifyDataSetChanged();

        rvKeys.setHasFixedSize(true);
        rvKeys.setLayoutManager(new LinearLayoutManager(requireActivity()));
        for(int i=0;i<10;i++)
            rvKeys.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        rvKeys.setAdapter(mAdapter);
    }

    private SearchView.OnClickListener cv_onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onClickFrag(mAdapter);
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