package com.example.sweetshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class CatTwoDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleTV,priceTV,quantityTV;
    Button BtnRemove;

    String userID,product_id,title,price,quantity;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_two_details);

        imageView=findViewById(R.id.image_dt);
        titleTV=findViewById(R.id.titleTV);
        priceTV=findViewById(R.id.price);
        quantityTV=findViewById(R.id.quantity);
        BtnRemove=findViewById(R.id.BtnRemove);

        Intent intent=getIntent();
        product_id=intent.getStringExtra("product_id");
        title=intent.getStringExtra("title");
        price=intent.getStringExtra("price");
        quantity=intent.getStringExtra("quantity");
        userID=intent.getStringExtra("userID");
        final String mImage=intent.getStringExtra("image");

        titleTV.setText(title);
        priceTV.setText(price + "/- per Kg");
        quantityTV.setText(quantity);

        Picasso.get().load(mImage).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(mImage).into(imageView);
            }


        });

        BtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("CartDB").child(userID).child(product_id);
                databaseReference.removeValue();

                Toast.makeText(getApplicationContext(),"Removed Successfully!",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}