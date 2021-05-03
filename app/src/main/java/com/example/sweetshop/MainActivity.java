package com.example.sweetshop;

import android.os.Bundle;

import com.example.sweetshop.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID,fullname,email,phonenumber;

    private BottomNavigationView bottomNavigationView;
    private DashboardFragment2 dashboardFragment;
    private HistoryFragment historyFragment;
    private InfoFragment infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    fullname = userProfile.fullname;
                    email = userProfile.email;
                    phonenumber = userProfile.phonenumber;

                } else {
                    Toast.makeText(getApplicationContext(), "You have logged in as a Guest", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Enable to fetch user details", Toast.LENGTH_LONG).show();
            }
        });

        dashboardFragment= new DashboardFragment2();
        historyFragment=new HistoryFragment();
        infoFragment=new InfoFragment();

        bottomNavigationView=findViewById(R.id.bottombar);
        setFragment(dashboardFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dashboard:
                        bottomNavigationView.setItemBackgroundResource(R.color.dashboard);
                        setFragment(dashboardFragment);
                        return true;
                    case R.id.history:
                        bottomNavigationView.setItemBackgroundResource(R.color.history);
                        setFragment(historyFragment);
                        return true;
                    case R.id.info:
                        bottomNavigationView.setItemBackgroundResource(R.color.info);
                        setFragment(infoFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe,fragment);
        fragmentTransaction.commit();
    }

}
