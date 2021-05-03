package com.example.sweetshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetshop.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {

    private DatabaseReference reference,reference2;
    private String userID;
    private TextView fullnameTV,emailTV,phonenumberTV;
    private EditText addressET;
    private Button btnSave;
    private String fullname,email,phonenumber,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fullnameTV = findViewById(R.id.fullnameTV);
        emailTV = findViewById(R.id.emailTV);
        phonenumberTV = findViewById(R.id.phonenumberTV);
        addressET = findViewById(R.id.addressET);
        btnSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userid");


        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference2 = FirebaseDatabase.getInstance().getReference("Address").child(userID);



        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    fullname = userProfile.fullname;
                    email = userProfile.email;
                    phonenumber = userProfile.phonenumber;
                    fullnameTV.setText(fullname);
                    emailTV.setText(email);
                    phonenumberTV.setText(phonenumber);

                } else {
                    Toast.makeText(getApplicationContext(), "You have logged in as a Guest", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Enable to fetch user details", Toast.LENGTH_LONG).show();
            }
        });

        reference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                address = snapshot.getValue(String.class);
                addressET.setText(address);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                address = snapshot.getValue(String.class);
                addressET.setText(address);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = addressET.getText().toString();
                if(address.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter your address!",Toast.LENGTH_LONG).show();
                }
                else{

                    HashMap hashMap = new HashMap();
                    hashMap.put("address",address);
                    reference2.setValue(hashMap);
                    Toast.makeText(getApplicationContext(),"Your address has been updated!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}