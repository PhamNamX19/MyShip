package com.example.huuph.myship.uis.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huuph.myship.FirebaseHelper.SignUpHelper;
import com.example.huuph.myship.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Signup extends AppCompatActivity {
    private EditText edUser;
    private EditText edPass;
    private EditText edRePass;
    private EditText edPhone;
    private Dialog dialogVerify;
    private ProgressDialog progressDialog;
    private EditText edDialogVerifyCode;
    private static String mVerificationId;
    private String mEmail;
    private String mPass;

    private static FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        edUser = findViewById(R.id.edSignupUser);
        edPass = findViewById(R.id.edSignupPass);
        edRePass = findViewById(R.id.edSignupRePass);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void onClickedSignUp(View view) {

        String user = edUser.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String repass = edRePass.getText().toString().trim();
        if (checkData(user, pass, repass)) {

            registerUser(user, pass);
        }


    }

    private void registerUser(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", "Den doan xac minh");
                    sendRegistrationLink();
                } else {
                    Log.d("TAG", "fail");
                }
            }
        });
    }

    private void sendRegistrationLink() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Da gui mail");
                            Toast.makeText(Signup.this,
                                    "Đã gửi email xác minh đến " + firebaseUser.getEmail(),
                                    Toast.LENGTH_LONG).show();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(Signup.this, MainActivity.class);
                            startActivity(intent);


                        } else {
                            Log.e("TAG", "sendEmailVerification", task.getException());
                            Toast.makeText(Signup.this,
                                    "Vui lòng kiểm tra lại email",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private boolean checkData(String user, String pass, String repass) {
        // kiem tra cac bien dau vao
        if (user.equals("") || pass.equals("") || repass.equals("")) {
            //thong bao ra man hinh
            Toast.makeText(this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.equals(repass) == false) {
            Toast.makeText(this, "Xác nhận mật khẩu sai", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.equals(repass)) {
            return true;
        }
        return false;
    }
}