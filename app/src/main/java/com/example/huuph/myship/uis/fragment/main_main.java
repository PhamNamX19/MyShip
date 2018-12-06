package com.example.huuph.myship.uis.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.huuph.myship.adapter.GestureMyShip;
import com.example.huuph.myship.uis.activities.MainActivity;
import com.example.huuph.myship.uis.activities.ThongBao;
import com.example.huuph.myship.adapter.PageAdapter;
import com.example.huuph.myship.R;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class main_main extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private String[] PERMISSION = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };

    private ActionBarDrawerToggle toggle;
    private PagerAdapter adapter;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private TextView tvUsername;
    private TextView tvUserEmail;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView imageView;

    private FirebaseStorage storage;
    private StorageReference storageReference;


    String email, name, id_facebook;
    String token;
    String testLoginEmail;
    ProfilePictureView profilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_main);

        if (!checkPermission()) {
            return;
        } else {
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();


            anhxa();
            initPager();
            setUpActionBar();
            initSliding();
            getInfo();
        }


        //nhan thong tin nguoi dung


    }


    private void anhxa() {
        profilePicture = (ProfilePictureView) findViewById(R.id.profilePicture);
        tvUsername = findViewById(R.id.tvUsername);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        profilePicture = findViewById(R.id.profilePicture);
        imageView = findViewById(R.id.v);
    }

    private void getInfo() {

        if (getIntent().getStringExtra("token") != null) {
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            id_facebook = getIntent().getStringExtra("id_facebook");
            token = getIntent().getStringExtra("token");
            testLoginEmail = getIntent().getStringExtra("LoginEmail");

            Log.d("JSONs", "ten" + name + "email" + email + "idfb:" + id_facebook);
            Log.d("TOKENS", token);
            tvUsername.setText(name);
            tvUserEmail.setText(email);
            profilePicture.setProfileId(id_facebook);
            //gui du lieu token sang fragment
            //an avatar
            if (testLoginEmail.equals("true")) {
                imageView.setVisibility(imageView.GONE);
            } else {
                profilePicture.setVisibility(profilePicture.GONE);
            }

        } else {
            tvUsername.setText("Khách");
            tvUserEmail.setText(" ");

        }
    }


    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSION) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSION, 0);
                    //ro fix bug
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!checkPermission()) {
            finish();
        }

    }

    private void initPager() {
        adapter = new PageAdapter(getSupportFragmentManager());
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        toolbar = findViewById(R.id.toolbar);
        // main = findViewById(R.id.main);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        drawerLayout.addDrawerListener(drawerToggle);


    }

    private void initSliding() {
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        Log.d("TAG", "CLGT");
        toggle.syncState();
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {

            FragmentNews.getInstance();
            Log.d("TAG", "CLGT");
        }
        if (i == 2) {
            FragmentHistory.getInstance();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public String getToken() {
        return token;
    }

    public void ThongBao(View view) {
        Intent intent = new Intent(this, ThongBao.class);
        startActivity(intent);
    }

    public void DangXuat(View view) {
        LoginManager.getInstance().logOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void HoTro(View view) {
    }

    public void HuongDan(View view) {
    }


    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void Upload(View view) {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    public void Download(View view) {


    }
}

