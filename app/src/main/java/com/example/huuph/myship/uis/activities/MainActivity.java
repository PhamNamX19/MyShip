package com.example.huuph.myship.uis.activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huuph.myship.R;
import com.example.huuph.myship.uis.fragment.main_main;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText edLoginUser;
    private EditText edLoginPass;
    private Button btfacebook;
    private TextView tvtes;
    private CheckBox cbSave;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String TAG = "TEST";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //mail va name ...facebook
    String email, name, id_facebook;
    String user;
    //ma token facebook
    private String token = "EAAGxzui9ezkBAPFd1ruZBZB4Ywvt6S5WvwZAR8drX6eIVp5E3zsctLchZBcZCoSl5cZARvShIi94F0npXxZBi4uCaXzA0LZBvgclEYgXTpRalLGAwT7TyR0kGUzr4SmPg9YpgIWClDwuZCwlzwd2NIw4YjkqIZCKGQRVJxttXKC5bZAzc1wz0Y0vRcQJ50JiI0bP5cZD";
    String[] perms = {"android.permission.FINE_LOCATION", "android.permission.CAMERA", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permsRequestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);

        }


        Initialization();
        setLogin_Button();
        //an button facebook mac dinh
        loginButton.setVisibility(View.GONE);
        loadData();
    }

    private void Initialization() {
        edLoginUser = findViewById(R.id.edLoginUser);
        edLoginPass = findViewById(R.id.edLoginPass);
        btfacebook = (Button) findViewById(R.id.btfacebook);
        tvtes = findViewById(R.id.tvtes);
        cbSave = findViewById(R.id.checkbox_remember);
        sharedPreferences = getSharedPreferences("UserSP", Context.MODE_PRIVATE);

        //Đăng nhập lại mỗi khi vào ứng dụng
        onStart();
        LoginManager.getInstance().logOut();
    }

    //luu du lieu vao sharepreference
    private void saveData(String username, String pass) {
        editor = sharedPreferences.edit();
        editor.putString("user", username);
        editor.putString("pass", pass);
        editor.putBoolean("remember", cbSave.isChecked());
        editor.commit();
    }

    //sharepreference va tai du lieu
    private void loadData() {
        if (sharedPreferences.getBoolean("remember", false)) {
            edLoginUser.setText(sharedPreferences.getString("user", ""));
            edLoginPass.setText(sharedPreferences.getString("pass", ""));
            cbSave.setChecked(true);
        } else {
            cbSave.setChecked(false);
        }

    }

    //login facebook
    private void setLogin_Button() {

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_facebook);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //TODO đăng nhập fb thành công
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                // token = loginResult.getAccessToken().getToken();
                Log.d("token", token);
                //lay thong tin nguoi dung
                result();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                //TODO Cancel fb
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                //TODO lỗi
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //dang nhap thanh cong
                    //todo lay thong tin avatar, mail
                    Log.d(TAG, "onAuthStateChanged:signed_in" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onclickLogin(View view) {
        //TODO gửi lên firrebase và check acc (Phước)
//        Intent intent1 = new Intent(MainActivity.this, main_main.class);
//        startActivity(intent1);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        user = edLoginUser.getText().toString().trim();
        String pass = edLoginPass.getText().toString().trim();
        if (user.equals("") || pass.equals("")) {
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if (cbSave.isChecked()) {
                saveData(user, pass);
                Log.d(TAG, "Saved");
            }
            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Loged in");
                        // chuyen sang cativity main
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công.Xin hãy đợi...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, main_main.class);
                        intent.putExtra("token", token);
                        intent.putExtra("LoginEmail", "false");
                        intent.putExtra("email", user);

                        startActivity(intent);
                    } else {
                        Log.d(TAG, "Login fail");
                        Log.d(TAG, task.toString());
                        Log.d(TAG, task.getException().toString());
                    }
                }
            });
        }
    }

    //lay thong tin avartar, name facfebook
    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                //xuất ra log thông tin id, name, mail khi đăng nhập thành công
                Log.d("JSON", response.getJSONObject().toString());

                //thấy thông tin
                try {
                    Intent intent = new Intent(MainActivity.this, main_main.class);
                    email = object.getString("email");
                    name = object.getString("name");
                    id_facebook = object.getString("id");
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("id_facebook", id_facebook);
                    intent.putExtra("token", token);
                    intent.putExtra("LoginEmail", "true");

                    finish();
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }


    public void onClickSignUp(View view) {
        //TODO Chuyển sang activity đăng kí
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);

    }

    public void onClickFogotpass(View view) {
        //TODO Chuyển sang layout quên mật khẩu
        Intent intent = new Intent(this, ForgotPass.class);
        startActivity(intent);
    }

    public void login_fabook_custom(View view) {
        if (view == btfacebook) {
            loginButton.performClick();
        }
    }

    public void onCheckboxClicked(View view) {

    }

    public void LoginGoogle(View view) {
        Toast.makeText(MainActivity.this, "Chức năng đang xây dựng", Toast.LENGTH_SHORT).show();
    }

    //xin quyen
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {

            case 200:

                boolean fineloction = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean coarselocation = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean readstorage = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                boolean writestorage = grantResults[4] == PackageManager.PERMISSION_GRANTED;

                break;

        }

    }
}
