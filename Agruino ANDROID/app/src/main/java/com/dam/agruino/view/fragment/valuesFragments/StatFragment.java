package com.dam.agruino.view.fragment.valuesFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.agruino.R;
import com.dam.agruino.controler.Tools;
import com.dam.agruino.model.Filter;
import com.dam.agruino.model.ValueHistory;
import com.dam.agruino.model.ValueLog;
import com.dam.agruino.view.adapters.AdapterLog;
import com.dam.agruino.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class StatFragment extends Fragment {

    private final static String PATHVALUES = "YC0HJwj1qynXC12LVpAV";
    private final static String DOCUMENTVALUES = "values";
    private final static String PATHHISTORY = "59DXbz5zbanZjBRhkTqv";
    private final static String DOCUMENTHISTORY = "values_history";
    private final static String DOCUMENTLOG = "values_log";
    private TextView tvTittle, tvCurrentValueStat, tvHistorical;
    private RecyclerView rvLog;
    private AdapterLog mAdapter;
    private ValueHistory valueHistory;
    private FirebaseFirestore db;
    private FirebaseFirestoreSettings settings;
    private NavController mNavC;
    private MainViewModel viewModel;
    private List<ValueLog> values;
    private List<ValueLog> valuesFilter;
    private ValueLog valueLog;

    public StatFragment() {
        // Required empty public constructor
    }

    public static StatFragment newInstance(Bundle args) {
        StatFragment fragment = new StatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch ((item.getItemId())) {
            case R.id.mnuFind:
                viewModel.setKey(valueHistory.getKey());
                mNavC.navigate(R.id.action_global_filterFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            valueHistory = getArguments().getParcelable("value");
        }

        //init
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
        values = new ArrayList<>();
        valuesFilter = new ArrayList<>();
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mNavC = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        setHasOptionsMenu(true);

        mAdapter = new AdapterLog();
        adquireValues(DOCUMENTVALUES, PATHVALUES);
        adquireValues(DOCUMENTHISTORY, PATHHISTORY);
        adquireValues(DOCUMENTLOG);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stat, container, false);
        //findbiewbyid
        tvTittle = v.findViewById(R.id.tvTittleStat);
        tvCurrentValueStat = v.findViewById(R.id.tvCurrentValueStat);
        tvHistorical = v.findViewById(R.id.tvHistoricalValue);
        rvLog = v.findViewById(R.id.rvLog);
        //init
        tvTittle.setText(valueHistory.getKey());
        //observe
        viewModel.getmFilter().observe(getActivity(), new Observer<Filter>() {
            @Override
            public void onChanged(Filter filter) {

                adquireValues(DOCUMENTVALUES, PATHVALUES);
                adquireValues(DOCUMENTHISTORY, PATHHISTORY);

                mAdapter.setValues(filter(string2long(viewModel.getDate())));
                mAdapter.notifyDataSetChanged();
                rvLog.setHasFixedSize(true);
                rvLog.setLayoutManager(new LinearLayoutManager(requireActivity()));
                rvLog.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
                rvLog.setAdapter(mAdapter);
            }
        });
        return v;
    }

    private List<ValueLog> filter(long date) {
        valuesFilter.clear();
        valueLog = viewModel.getValueLog();
        for (ValueLog v : values) {
            if (v.getDateUnix() >= date)
                valuesFilter.add(v);
        }
        return valuesFilter;
    }

    public void updateRV() {
        // Init RecyclerView
        mAdapter.setValues(values);

        mAdapter.notifyDataSetChanged();

        rvLog.setHasFixedSize(true);
        rvLog.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvLog.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        rvLog.setAdapter(mAdapter);
    }

    private long string2long(String date) {
        Date fechDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fechDate = format.parse(date);
            return fechDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    
    public void adquireValues(final String document, String pathDocument) {
        final DocumentReference docRef = db.collection(document).document(pathDocument);
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
                    if (document.equalsIgnoreCase(DOCUMENTVALUES)) {
                        valueHistory.setValue(Float.parseFloat(Objects.toString(snapshot.get("moisture"))));
                    } else {
                        valueHistory.setValueHistory((Float.parseFloat(Objects.toString(snapshot.get(valueHistory.getKey().toLowerCase())))));
                    }
                } else {
                    Log.d("agruino", "Current data: null");
                    valueHistory.setValue(0);
                    valueHistory.setValueHistory(0);
                }
                tvCurrentValueStat.setText(String.format("%.2f", valueHistory.getValue()) + " " + valueHistory.getMeasure());
                tvHistorical.setText(String.format("%.2f", (valueHistory.getValueHistory() / 365) * Tools.calcDayOfTear()) + " " + valueHistory.getMeasure());
            }
        });
    }

    public void adquireValues(String document) {
        db.collection(document)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String key = valueHistory.getKey().toLowerCase();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                valueLog = new ValueLog(valueHistory);
                                valueLog.setValue(Float.parseFloat(Objects.toString(document.get(key))));
                                valueLog.setDate(Objects.toString(document.get("dateString")));
                                valueLog.setTime(Objects.toString(document.get("time")));
                                valueLog.setDateUnix(Long.parseLong(Objects.toString(document.get("date"))));
                                Log.d("agruino", "aki" + valueLog.getValueHistory());
                                values.add(valueLog);
                                viewModel.setValueLog(valueLog);
                                updateRV();
                            }
                            viewModel.setValues(values);
                        } else {
                            Log.d("agruino", "Error getting documents: ", task.getException());
                        }
                    }
                });
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