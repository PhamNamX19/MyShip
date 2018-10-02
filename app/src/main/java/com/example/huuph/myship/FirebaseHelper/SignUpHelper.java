package com.example.huuph.myship.FirebaseHelper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpHelper {
    public static void signUp(String email, String pass, final Activity activity) {

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(activity, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", String.valueOf(task.getException()));
                        } else {
                            //todo go to home page
                            Log.d("TAG", String.valueOf(task.getException()));
                        }
                    }
                });
    }
}