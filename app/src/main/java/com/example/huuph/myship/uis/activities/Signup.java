package com.example.huuph.myship.uis.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        edUser = findViewById(R.id.edSignupUser);
        edPass = findViewById(R.id.edSignupPass);
        edRePass = findViewById(R.id.edSignupRePass);
        edPhone = findViewById(R.id.edSignupPhone);
        progressDialog = new ProgressDialog(this);
    }

    public void onClickedSignUp(View view) {

        //gửi sang verify phone
        String user = edUser.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String repass = edRePass.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        if(checkData(user,pass,repass,phone)){

            progressDialog.setTitle("Please wait...");
            progressDialog.show();
            creatCode(phone,Signup.this);
            dialogVerify = new Dialog(this);
            dialogVerify.setContentView(R.layout.dialog_signup);
            progressDialog.hide();
            dialogVerify.show();

            mEmail = user;
            mPass = pass;

        }


    }
    public void onClickedButtonDialog(View view) {
        progressDialog.show();
        edDialogVerifyCode = dialogVerify.findViewById(R.id.edDialogVerifyCode);
        String verifyCode = edDialogVerifyCode.getText().toString().trim();
        checkCode(mVerificationId,verifyCode,Signup.this);
        Log.d("TAG","Checked code");
    }





    private boolean checkData(String user,String pass,String repass,String phone){
        // kiem tra cac bien dau vao
        if(user.equals("")||pass.equals("")||repass.equals("")||phone.equals("")){
            //thong bao ra man hinh
            Toast.makeText(this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(pass.equals(repass) == false){
            Toast.makeText(this, "Xác nhận mật khẩu sai", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(pass.equals(repass)){
            return true;
        }
        return false;
    }

    private void creatCode(String phoneNumber, Activity activity){
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                //todo create acc
                creatAcc(mEmail,mPass);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d("TAG", e.toString());

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, activity, mCallbacks);
        Log.d("TAG", "Created Code");

    }
    private void checkCode(String verifycationID,String verifyCodeInput, Activity activity) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Khoi tao doi tuong kiem tra: Id của sesion va code nguoi dung nhap
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifycationID, verifyCodeInput);
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("TAG", task.toString());
                    //todo: gọi phương thức tạo tài khoản từ user
                    dialogVerify.hide();
                    creatAcc(mEmail,mPass);
                } else {
                    Log.d("TAG","Code error");
                }
            }
        });
    }
    private void creatAcc(String mEmail, String mPass){
        SignUpHelper.signUpWithEmail(mEmail,mPass, Signup.this);
        progressDialog.hide();
        Log.d("TAG","Created account");
    }


}
