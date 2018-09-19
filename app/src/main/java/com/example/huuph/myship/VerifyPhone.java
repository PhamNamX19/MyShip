package com.example.huuph.myship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {
    private String user;
    private String pass;
    private String phone;
    private String codeVerify;
    private String mVerificationId;
    private String mVerificationIfd;

    private PhoneAuthCredential credential;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;

    private EditText edCodeVerify;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phone);
        // nhan du lieu
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        pass = intent.getStringExtra("pass");
        phone = intent.getStringExtra("phone");


        // hkoi toa doi tuong
        edCodeVerify = findViewById(R.id.edVerify);
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Toast.makeText(VerifyPhone.this, "Xác minh thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d("Tag", e.toString());
                Toast.makeText(VerifyPhone.this, "Vui lòng kiểm tra lại số điện thoại", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                Toast.makeText(VerifyPhone.this, "Đã gửi mà xác minh, mã xác minh chỉ có hiệu lực 60s", Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId;
            }
        };

        // tao code Verify
        creatCode(phone);


    }

    public void onClickedVerify(View view) {
        String verifyCode = edCodeVerify.getText().toString().trim();
        if (verifyCode.equals("")) {
            Toast.makeText(this, "Hãy nhập mã xác minh", Toast.LENGTH_SHORT).show();
        } else {
            checkCode(verifyCode);
        }

    }

    private void creatCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, VerifyPhone.this, mCallbacks);
        Log.d("TAG", "Created Code");

        // OnVerificationStateChangedCallbacks
    }

    /**
     * Khởi tạo đối tượng credential từ mã code và id phiên để tiến hành xác minh
     *
     * @param verifyCodeInput : mã code nhận được
     *                        mVerificationId: id của code được firebase gửi
     * @return
     */
    private void checkCode(String verifyCodeInput) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verifyCodeInput);
        mAuth.signInWithCredential(credential).addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", task.toString());
                    Toast.makeText(VerifyPhone.this, "Xác Minh thành công", Toast.LENGTH_SHORT).show();
                    //todo: gọi phương thức tạo tài khoản từ user
                } else {
                    Toast.makeText(VerifyPhone.this, "Vui lòng kiểm tra mã xác minh hoặc số điện thoại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
