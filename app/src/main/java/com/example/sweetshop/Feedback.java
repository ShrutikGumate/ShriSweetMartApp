package com.example.sweetshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Feedback extends AppCompatActivity {

    private DatabaseReference reference;
    private String userID,feeback;

    private TextInputEditText feedbackET;
    private Button BtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userid");

        reference = FirebaseDatabase.getInstance().getReference("Feedback").child(userID);

        feedbackET=findViewById(R.id.feedbackET);
        BtnSend = findViewById(R.id.buttonSend);

        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feeback = feedbackET.getText().toString();
                if(feeback.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter your feedback!",Toast.LENGTH_LONG).show();
                }
                else{
                    String key = reference.push().getKey();

                    HashMap hashMap = new HashMap();
                    hashMap.put(key,feeback);
                    reference.setValue(hashMap);
                    Toast.makeText(getApplicationContext(),"Thank you! Your Feedback has been sent!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}