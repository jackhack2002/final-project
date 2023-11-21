package com.jackhack2002.mychat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_Activity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView usernameTextView;
    private TextView emailTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        profileImageView = findViewById(R.id.profile_photo);
        usernameTextView = findViewById(R.id.username);
        emailTextView = findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Retrieve user data from the database
            DatabaseReference userRef = mDatabase.child("users").child(userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("username").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);

                        // Set the retrieved data to the views
                        usernameTextView.setText(username);
                        emailTextView.setText(email);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
    }
}
