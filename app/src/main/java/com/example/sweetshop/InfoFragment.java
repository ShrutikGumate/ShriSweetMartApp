package com.example.sweetshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetshop.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InfoFragment extends Fragment {
    private Button btnLogout,btnOrderHistory,btnUpdate,btnFeedback,btnContact,btnAboutUs;
    private TextView titleTextView;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID,fullname,email,phonenumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View myview = inflater.inflate(R.layout.fragment_info, container, false);

        btnLogout = (Button) myview.findViewById(R.id.buttonLogout);
        btnOrderHistory = myview.findViewById(R.id.buttonOrderHistory);
        btnUpdate=myview.findViewById(R.id.buttonUpdate);
        btnFeedback=myview.findViewById(R.id.buttonFeedback);
        btnContact=myview.findViewById(R.id.buttonContact);
        btnAboutUs=myview.findViewById(R.id.buttonAboutUs);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    fullname = userProfile.fullname;
                    email = userProfile.email;
                    phonenumber = userProfile.phonenumber;

                    titleTextView = myview.findViewById(R.id.title);
                    titleTextView.setText(fullname);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(myview.getContext(),"Enable to fetch user details",Toast.LENGTH_LONG).show();
            }
        });

        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myview.getContext(),OrderHistory.class);
                startActivity(intent);

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myview.getContext(),UserProfile.class);
                intent.putExtra("userid",userID);
                startActivity(intent);

            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myview.getContext(),Feedback.class);
                intent.putExtra("userid",userID);
                startActivity(intent);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myview.getContext(),ContactUs.class);
                startActivity(intent);
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myview.getContext(),AboutUs.class);
                startActivity(intent);

            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(myview.getContext(),"Successfully Log out",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(myview.getContext(),Login.class);
                startActivity(intent);

            }
        });



        return myview;
    }

}