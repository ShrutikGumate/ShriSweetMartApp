package com.example.sweetshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sweetshop.Model.Contactdata;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactUs extends AppCompatActivity {

    DatabaseReference reference;
    TextView phonenumberTV,emailTV,websiteTV,youtubeTV,instagramTV,twitterTV;
    Contactdata data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        phonenumberTV=findViewById(R.id.phonenumberTV);
        emailTV=findViewById(R.id.emailTV);
        websiteTV=findViewById(R.id.websiteTV);
        instagramTV=findViewById(R.id.instagramTV);
        youtubeTV=findViewById(R.id.youtubeTV);
        twitterTV=findViewById(R.id.twitterTV);

        reference = FirebaseDatabase.getInstance().getReference("ContactUs");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = snapshot.getValue(Contactdata.class);
                phonenumberTV.setText(data.getPhonenumber());
                emailTV.setText(data.getEmail());
                websiteTV.setText(data.getWebsite());
                instagramTV.setText(data.getInstagram());
                youtubeTV.setText(data.getYoutube());
                twitterTV.setText(data.getTwitter());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}