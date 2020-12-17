package com.dam.agruino.view.fragment.valuesFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dam.agruino.R;
import com.dam.agruino.model.Filter;
import com.dam.agruino.viewmodel.MainViewModel;

public class FilterFragment extends Fragment {
    private TextView tvTittle,tvDate;
    private Button btAccept, btCancel, btDate;
    private OnFilterFrament mListener;
    private MainViewModel viewModel;
    private Filter filter;

    public interface OnFilterFrament {
        void onAccept();

        void onCancel();

        void onClickDate();
    }

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(Bundle args) {
        FilterFragment fragment = new FilterFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (context instanceof FilterFragment.OnFilterFrament) {
            mListener = (OnFilterFrament) context;

        } else {
            throw new RuntimeException(context.toString() + " must implement OnFilterFrament");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_filter, container, false);
        //find
        tvTittle = v.findViewById(R.id.tvTittleFilter);
        tvDate = v.findViewById(R.id.tvDate);
        btAccept = v.findViewById(R.id.btAcceptFilter);
        btCancel = v.findViewById(R.id.btCancelFilter);
        btDate = v.findViewById(R.id.btDate);
        //init
        filter =new Filter();
        tvTittle.setText(getString(R.string.filterFrafment)+" "+viewModel.getKey());tvDate.setText(viewModel.getDate());
        viewModel.getmDate().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                    filter.setDate(viewModel.getDate());

                    viewModel.setmFilter(filter);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //listener
        btAccept.setOnClickListener(bt_click);
        btCancel.setOnClickListener(bt_click);
        btDate.setOnClickListener(bt_click);

    }

    private final View.OnClickListener bt_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btAcceptFilter:
                    mListener.onAccept();
                    break;
                case R.id.btCancelFilter:
                    mListener.onCancel();
                    break;
                case R.id.btDate:
                    mListener.onClickDate();
                    break;
            }

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