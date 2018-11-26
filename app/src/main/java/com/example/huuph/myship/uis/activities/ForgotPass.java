package com.example.huuph.myship.uis.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huuph.myship.R;

public class ForgotPass extends AppCompatActivity{
  private EditText edForgotUser;
  private EditText edForgotPhone;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.forgotpass);
    edForgotUser = findViewById(R.id.edForgotUser);
    edForgotPhone = findViewById(R.id.edForgotPhone);
  }

  public void ocClickXacNhan(View view) {
    String user = edForgotUser.getText().toString().trim();
    String phone = edForgotPhone.getText().toString().trim();
    if(user.equals("") || phone.equals("")){
      Toast.makeText(this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
    }
    else {
      //TODO up lên và check tại server
    }
  }
}
