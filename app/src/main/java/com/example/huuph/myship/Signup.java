package com.example.huuph.myship;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    private EditText edUser;
    private EditText edPass;
    private EditText edRePass;
    private EditText edPhone;
    private EditText hihi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        edUser = findViewById(R.id.edSignupUser);
        edPass = findViewById(R.id.edSignupPass);
        edRePass = findViewById(R.id.edSignupRePass);
        edPhone = findViewById(R.id.edSignupPhone);
    }

    public void onClickedSignUp(View view) {

        //gửi sang verify phone
        String user = edUser.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String repass = edRePass.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        if(checkData(user,pass,repass,phone)){
            Intent intent = new Intent(this, VerifyPhone.class);
            intent.putExtra("user",user);
            intent.putExtra("pass",pass);
            intent.putExtra("phone",phone);

            startActivity(intent);
        }


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
}
