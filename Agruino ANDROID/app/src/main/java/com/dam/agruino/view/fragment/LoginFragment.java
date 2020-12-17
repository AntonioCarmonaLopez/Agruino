package com.dam.agruino.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dam.agruino.R;
import com.dam.agruino.model.User;
import com.dam.agruino.view.MainActivity;

public class LoginFragment extends Fragment {

    private EditText etUser,etPass;
    private Button btAccept,btCancel;

    private LoginFragInterface mListener;
    public static boolean main;

    public interface LoginFragInterface {

        void onCancelarLoginFrag();

        void onAceptarLoginFrag(User user);

    }

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(Bundle arguments) {
        LoginFragment fragment = new LoginFragment();
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragInterface) {
            mListener = (LoginFragInterface) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LoginFragInterface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_login, container, false);

        // FindViewByIds
        etUser = v.findViewById(R.id.etUsername);
        etPass = v.findViewById(R.id.etPass);
        btCancel = v.findViewById(R.id.btCancel);
        btAccept = v.findViewById(R.id.btAccept);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main =true;
        //listener
        btAccept.setOnClickListener(bt_click);
        btCancel.setOnClickListener(bt_click);
    }

    private View.OnClickListener bt_click= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btAccept:
                    User user = new User(etUser.getText().toString(),etPass.getText().toString());
                    mListener.onAceptarLoginFrag(user);
                    break;
                case R.id.btCancel:
                    mListener.onCancelarLoginFrag();
                    break;
            }

        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}