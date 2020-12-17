package com.dam.agruino.controler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dam.agruino.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthGoogle {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private Context mContext;

    public AuthGoogle(Context context) {
        mContext = context;
    }

    public FirebaseUser signIn(String username,String pass) {

        Log.d("agruino", "signIn:" + username + pass);

        auth.signInWithEmailAndPassword(username, pass)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("agruino", "signInWithEmail:success");
                            user = auth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("agruino", "signInWithEmail:failure");
                        }
                    }
                });
        return user;

    }


    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}

