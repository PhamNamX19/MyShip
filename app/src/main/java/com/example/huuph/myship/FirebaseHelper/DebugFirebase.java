package com.example.huuph.myship.FirebaseHelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.huuph.myship.R;
import com.example.huuph.myship.UserManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DebugFirebase extends AppCompatActivity{
    private EditText edU;
    private EditText edP;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debugfirebase);
        edU = findViewById(R.id.edDbU);
        edP = findViewById(R.id.edDbP);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("users");



    }

    public void clikedDebug(View view) {
        String name = edU.getText().toString().trim();
        String email = edP.getText().toString().trim();
        createUser(name, email);

    }
    private void createUser(String name, String email) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mDatabase.push().getKey();
        }

        UserManager user = new UserManager(name, email);

        mDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    private void addUserChangeListener() {
        mDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserManager user = dataSnapshot.getValue(UserManager.class);
                // Check for null
                if (user == null) {
                    Log.d("TAG", "User data is null!");
                    return;
                }
                Log.d("TAG", "User data is changed!" + user.name + ", " + user.email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
