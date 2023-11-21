package com.jackhack2002.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackhack2002.mychat.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    String name,email,password;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();

                login();
            }
        });
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.name.getText().toString();
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();

                signUp();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            startActivity(new Intent(AuthActivity.this,MainActivity.class));
            finish();
        }
    }

    private void login() {
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(AuthActivity.this,MainActivity.class));
                        finish();
                    }
                });
    }
    private void signUp() {
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest= new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(),name,email,password);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                        startActivity(new Intent(AuthActivity.this,MainActivity.class));
                        finish();
                    }
                });
    }
}