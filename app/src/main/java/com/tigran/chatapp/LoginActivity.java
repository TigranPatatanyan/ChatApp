package com.tigran.chatapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.view.View.GONE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final int GALLERY_REQUEST_CODE = 2020;
    private ImageView imageView;
    private EditText etName;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private View mLinearLayout;
    private Button mButtonSignIn;
    private Button mButtonRegister;
    DatabaseReference refUsers;
    Uri selectedImage;

    int back = 0;
    private FirebaseAuth mAuth;
    public FirebaseUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.m_iv_avatar);
        imageView.setOnClickListener(this);
        etName = findViewById(R.id.et_name);
        mEditTextUsername = findViewById(R.id.et_username);
        mEditTextUsername.setOnClickListener(this);
        mEditTextPassword = findViewById(R.id.et_password);
        mEditTextPassword.setOnClickListener(this);
        mButtonSignIn = findViewById(R.id.btn_sign_in);
        mButtonSignIn.setOnClickListener(this);
        mButtonRegister = findViewById(R.id.btn_register);
        mButtonRegister.setOnClickListener(this);
        mButtonRegister.setVisibility(GONE);
        TextView mTextViewRegisterNow = findViewById(R.id.tw_register);
        mTextViewRegisterNow.setOnClickListener(this);
        mLinearLayout = findViewById(R.id.lyt_register_now);
        mEditTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && isNetworkAvailable(LoginActivity.this)) {
                    if (back == 0) {
                        signIn(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString());
                    } else {
                        register(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString());
                    }
                    return true;
                } else {
                    Toast.makeText(LoginActivity.this, "no internet", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tw_register:
                back = 0;
                mLinearLayout.setVisibility(View.INVISIBLE);
                mButtonSignIn.setVisibility(GONE);
                mEditTextUsername.setText("");
                mEditTextPassword.setText("");
                mButtonRegister.setVisibility(View.VISIBLE);
                etName.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_sign_in:
                if (isNetworkAvailable(this)) {
                    signIn(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, "no internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_register:
                if (isNetworkAvailable(this)) {
                    if (!etName.getText().toString().equals("")) {
                        register(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString());
                    } else
                        Toast.makeText(LoginActivity.this, "please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "no internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.m_iv_avatar:
                pickFromGallery();
                break;
        }
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                    Glide.with(this).load(selectedImage).apply(RequestOptions.circleCropTransform()).into(imageView);
                    break;
            }
    }

    private void signIn(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void register(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myUser = mAuth.getCurrentUser();
                            String uid = myUser.getUid();
                            refUsers = FirebaseDatabase.getInstance().getReference("users").child(uid);
                            HashMap<String, String> user = new HashMap<>();
                            user.put("id", uid);
                            user.put("name", etName.getText().toString());
                            if (selectedImage != null) {
                                user.put("imageURL", selectedImage.toString());
                            } else {
                                user.put("imageURL", "default");
                            }
                            refUsers.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (back == 0) {
            mButtonRegister.setVisibility(GONE);
            mLinearLayout.setVisibility(View.VISIBLE);
            mButtonSignIn.setVisibility(View.VISIBLE);
            back = 1;
            mEditTextPassword.setText("");
            mEditTextUsername.setText("");
            imageView.setVisibility(View.INVISIBLE);
            etName.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }


}
