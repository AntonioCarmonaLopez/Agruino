package com.dam.agruino.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dam.agruino.R;
import com.dam.agruino.controler.AuthGoogle;
import com.dam.agruino.model.Filter;
import com.dam.agruino.model.Key;
import com.dam.agruino.model.User;
import com.dam.agruino.model.Value;
import com.dam.agruino.model.ValueHistory;
import com.dam.agruino.model.ValueLog;
import com.dam.agruino.view.adapters.AdapterKeys;
import com.dam.agruino.view.adapters.AdapterStats;
import com.dam.agruino.view.adapters.AdapterWaterKeys;
import com.dam.agruino.view.dialog.DlgAlerta;
import com.dam.agruino.view.dialog.DlgConfirmacion;
import com.dam.agruino.view.fragment.LoginFragment;
import com.dam.agruino.view.fragment.StatsFragment;
import com.dam.agruino.view.fragment.valuesFragments.FilterFragment;
import com.dam.agruino.view.fragment.valuesFragments.KeysFragment;
import com.dam.agruino.view.fragment.valuesFragments.WaterFragment;
import com.dam.agruino.view.dialog.DlgSeleccionFecha;
import com.dam.agruino.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.LoginFragInterface,
        KeysFragment.KeyFragInterface,
        WaterFragment.OnWaterFragment,
        DlgConfirmacion.DlgConfirmacionListener,
        StatsFragment.OnStatsFrag,
        FilterFragment.OnFilterFrament,
        DlgSeleccionFecha.DlgSeleccionFechaListener {

    private final String exit = "EXIT";
    private NavController mNavC;
    private ArrayList<String> colWaterKeys;
    private ArrayList<Key> keys;
    private ArrayList<Value> colKeys;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private Key key;
    private Value value;
    private ValueHistory valueHistory;
    private ValueLog valueLog;
    private boolean loginActive;
    private Filter filter;
    private List<ValueLog> valueLogs;
    private String keyLog;
    private MainViewModel mMainVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //inits
        filter = new Filter();
        mMainVM = new ViewModelProvider(this).get(MainViewModel.class);
        if (savedInstanceState != null) {
            loginActive = savedInstanceState.getBoolean("login");
            LoginFragment.main = savedInstanceState.getBoolean(("fragLogin"));
            filter = savedInstanceState.getParcelable("filter");
        }

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //init arrays
        colWaterKeys = new ArrayList<>();
        keys = new ArrayList<>();
        colKeys =new ArrayList<>();
        //add to  waterKeys collection
        colWaterKeys.add(WATERKEYS.Conductivity.toString());
        colWaterKeys.add(WATERKEYS.Turbidity.toString());
        colWaterKeys.add(WATERKEYS.Temperature.toString());
        colWaterKeys.add(WATERKEYS.PH.toString());
        //add add to keys collectection

        //item 1
        key = new Key();
        key.setKey("Moisture");

        key.setIcon(R.drawable.moisture);
        keys.add(key);
        //item 2
        key = new Key();
        key.setKey("Water Values");
        key.setIcon(R.drawable.water);
        keys.add(key);
        //item 3
        key = new Key();
        key.setKey("Stats");
        key.setIcon(R.drawable.grafica);
        keys.add(key);
        //add valuel to colKeys
        value = new Value("Moisture","%");
        colKeys.add(value);
        value = new Value("Conductivity","MTU");
        colKeys.add(value);
        value = new Value("Turbidity","Degrees of transparency");
        colKeys.add(value);
        value = new Value("PH","");
        colKeys.add(value);
        value = new Value("Temp","Degrees");
        colKeys.add(value);
        //findviewbyid
        mNavC = Navigation.findNavController(this, R.id.nav_host_fragment);

        //throw fragment login
        if (!loginActive) {
            mNavC.navigateUp();
            mNavC.navigate(R.id.action_mainFragment_to_loginFragment);
        }

    }

    @Override
    public void OnclickWaterFragment(AdapterWaterKeys mAdapter) {
        String key;
        Bundle bundle = new Bundle();
        key = mAdapter.getItem(mAdapter.getItemPos());
        if (key.equalsIgnoreCase(WATERKEYS.Turbidity.toString()))
            value = new Value(WATERKEYS.Turbidity.toString(), "Degrees of transparency");
        else if (key.equalsIgnoreCase(WATERKEYS.Conductivity.toString()))
            value = new Value(WATERKEYS.Conductivity.toString(), "MTU");
        else if (key.equalsIgnoreCase(WATERKEYS.Temperature.toString()))
            value = new Value(WATERKEYS.Temperature.toString(), "Degrees centigrade");
        else
            value = new Value(WATERKEYS.PH.toString(), "");
        bundle.putParcelable("value", value);

        mNavC.navigate(R.id.action_waterFragment_to_waterValuesFragment, bundle);
    }

    @Override
    public void onDlgConfirmacionPositiveClick(DialogFragment dialog) {
        finish();
    }

    @Override
    public void onDlgConfirmacionNegativeClick(DialogFragment dialog) {
        mNavC.navigate(R.id.action_mainFragment_to_loginFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.mnuExit:
                dialogExit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (LoginFragment.main)
            dialogExit();
    }

    private void dialogExit() {
        DlgConfirmacion dlg = new DlgConfirmacion();
        dlg.setTitulo(R.string.app_name);
        dlg.setMensaje(R.string.conf_exit);
        dlg.show(getSupportFragmentManager(), exit);
    }

    @Override
    public void onCancelarLoginFrag() {
        //mNavC.navigateUp();
        mNavC.navigate(R.id.action_loginFragment_self);
    }

    @Override
    public void onAceptarLoginFrag(User user) {
        //mNavC.navigateUp();
        loginActive = true;
        LoginFragment.main = false;

        if (user.getUsername().equals("") || user.getPassword().equals("")) {
            if (user.getUsername().equals("") && user.getPassword().equals("")) {
                DlgAlerta da = new DlgAlerta();
                da.setTitulo(R.string.alert);
                da.setMensaje(R.string.upempty);
                da.show(getSupportFragmentManager(), "alert");
            } else if (user.getUsername().equals("")) {
                DlgAlerta da = new DlgAlerta();
                da.setTitulo(R.string.alert);
                da.setMensaje(R.string.uempty);
                da.show(getSupportFragmentManager(), "alert");
            } else {
                DlgAlerta da = new DlgAlerta();
                da.setMensaje(R.string.pempty);
                da.show(getSupportFragmentManager(), "alert");
                mNavC.navigateUp();
                mNavC.navigate(R.id.action_mainFragment_to_loginFragment);
            }
        } else
            signIn(user.getUsername(),user.getPassword());

    }

    private void signIn(String username, String pass) {
        mAuth.signInWithEmailAndPassword(username, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("agruino", "signInWithEmail:success");
                            mUser = mAuth.getCurrentUser();
                            if(mUser != null) {
                                Bundle args = new Bundle();
                                args.putParcelableArrayList("keys", keys);
                                mNavC.navigate(R.id.action_loginFragment_to_keysFragment, args);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("agruino", "signInWithEmail:failure");
                            DlgAlerta da = new DlgAlerta();
                            da.setTitulo(R.string.alert);
                            da.setMensaje(R.string.loginko);
                            da.show(getSupportFragmentManager(), "alert");
                            mNavC.navigate(R.id.action_loginFragment_self);
                        }
                    }
                });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("login", loginActive);
        outState.putBoolean("fragLogin", LoginFragment.main);
        outState.putParcelable("filter",filter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        new AuthGoogle(this).logout();
        super.onDestroy();
    }

    @Override
    public void onClickFrag(AdapterKeys adapterKeys) {
        int pos = adapterKeys.getItemPos();
        key = keys.get(pos);
        Bundle args = new Bundle();
        if (key.getKey().equalsIgnoreCase("moisture")) {
            value = new Value("Moisture", "%");
            args.putParcelable("value",value);
            mNavC.navigate(R.id.action_keysFragment_to_moistureFragment, args);
        } else if(key.getKey().equalsIgnoreCase("water values")){
            args.putStringArrayList("waterKeys",colWaterKeys);
            mNavC.navigate(R.id.action_keysFragment_to_waterFragment,args);
        } else {
            args.putParcelableArrayList("keys",colKeys);
            Log.d("agruino",colKeys.get(0).getKey());
            mNavC.navigate(R.id.action_keysFragment_to_statsFragment,args);
        }
    }

    @Override
    public void onClickStats(AdapterStats adapterStats) {
        int pos = adapterStats.getItemPos();
        Bundle args = new Bundle();
        valueHistory = new ValueHistory(value = colKeys.get(pos));
        args.putParcelable("value",valueHistory);
        //mNavC.navigateUp();
        mNavC.navigate(R.id.action_statsFragment_to_statFragment,args);

    }

    @Override
    public void onAccept() {

        mNavC.navigateUp();
        mMainVM.setmDate(mMainVM.getDate());
//        mNavC.navigate(R.id.action_mainFragment_to_statFragment);
    }



    @Override
    public void onCancel() {

    }

    @Override
    public void onClickDate() {

        openDialogDate();

    }

    private void openDialogDate() {
        DlgSeleccionFecha dsf = new DlgSeleccionFecha();
        dsf.show(getSupportFragmentManager(), "selectDate");
    }

    @Override
    public void onDlgSeleccionFechaClick(DialogFragment dialog, int year, int month, int day) {

        StringBuilder date = new StringBuilder();
        date.append(year).append("-").append(month).append("-").append(day);
        mMainVM.setDate(date.toString());

        mNavC.navigateUp();
        mNavC.navigate(R.id.action_global_filterFragment);

    }

    private enum WATERKEYS {Conductivity, Turbidity, Temperature, PH}
}