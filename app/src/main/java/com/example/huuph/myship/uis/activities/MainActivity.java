package com.example.huuph.myship.uis.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huuph.myship.ForgotPass;
import com.example.huuph.myship.R;
import com.example.huuph.myship.Signup;
import com.example.huuph.myship.uis.main_main;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText edLoginUser;
    private EditText edLoginPass;

    private ProfilePictureView profile;

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    String email, name, id_facebook; //mail va name facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialization();

        //xin quyền facebook
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        setLogin_Button();
    }

    private void Initialization() {
        Log.d("TAG","Init successfull");
        edLoginUser = (EditText) findViewById(R.id.edLoginUser);
        edLoginPass = (EditText) findViewById(R.id.edLoginPass);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_facebook);
        //đăng nhập lại mỗi khi vào ứng dụng
        onStart();
    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //TODO đăng nhập fb thành công
                result();//lấy thông tin người dùng về

            }

            @Override
            public void onCancel() {
                //TODO Cancel fb
            }

            @Override
            public void onError(FacebookException error) {
                //TODO lỗi
            }
        });
    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                //xuất ra log thông tin id, name, mail khi đăng nhập thành công
                Log.d("JSON", response.getJSONObject().toString());
                //thấy thông tin
                try {
                    email = object.getString("email");
                    name = object.getString("name");
                    id_facebook = object.getString("id");
                    Toast.makeText(MainActivity.this, email, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();

    }

    public void onclickLogin(View view) {
        //TODO gửi lên firrebase và check acc (Phước)
        Intent intent = new Intent(this,main_main.class);
        startActivity(intent);
    }


    public void onClickSignUp(View view) {
        //TODO Chuyển sang activity đăng kí
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }

    public void onClickFogotpass(View view) {
        //TODO Chuyển sang layout quên mật khẩu
        Intent intent = new Intent(this,ForgotPass.class);
        startActivity(intent);
    }
}
