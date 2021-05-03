package com.example.sweetshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetshop.Model.Cartdata;
import com.example.sweetshop.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class CatOneDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView title,description,price;
    private EditText quantity;
    private Button AddtoCart;

    private FirebaseUser user;
    DatabaseReference cartdbreference;
    private String userID,mTitle,mDescription,product_id;
    private String mPrice;
    private String total_quantity;

    String mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_one_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        cartdbreference = FirebaseDatabase.getInstance().getReference().child("CartDB");

        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imageView=findViewById(R.id.image_dt);
        title=findViewById(R.id.title_dt);
        description=findViewById(R.id.description_dt);
        price=findViewById(R.id.price_dt);
        quantity=findViewById(R.id.quantity_dt);
        AddtoCart=findViewById(R.id.btnAddtoCart);

        Intent intent=getIntent();
        mTitle=intent.getStringExtra("title");
        mDescription=intent.getStringExtra("description");
        mPrice=intent.getStringExtra("price");
        mImage=intent.getStringExtra("image");
        product_id=intent.getStringExtra("id");
        total_quantity=intent.getStringExtra("total_quantity");

        title.setText(mTitle);
        description.setText(mDescription);
        price.setText(mPrice + "/- per Kg");

        Picasso.get().load(mImage).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(mImage).into(imageView);
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        AddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertintocart();

            }
        });
    }

    public void insertintocart(){
        String str_quantity=quantity.getText().toString().trim();
        if (str_quantity.equals("")){
            Toast.makeText(getApplicationContext(),"please enter quantity",Toast.LENGTH_SHORT).show();
        }
        else {
            int Quantity = Integer.parseInt(str_quantity);
            if (Quantity == 0) {
                Toast.makeText(getApplicationContext(), "please enter at least 1", Toast.LENGTH_SHORT).show();
            } else {

                Cartdata cartdata = new Cartdata(Quantity,mImage,mTitle,mDescription,Integer.parseInt(mPrice),product_id,Integer.parseInt(total_quantity));

                cartdbreference.child(userID).child(product_id).setValue(cartdata);


                Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
                closekeyboard();
            }
        }

    }

    public void closekeyboard(){
        View view=this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

    }
}