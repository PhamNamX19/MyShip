package com.example.huuph.myship.FirebaseHelper;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class SignUpHelper {
    public void signUpCustomUser(String user,String pass){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
    }
}