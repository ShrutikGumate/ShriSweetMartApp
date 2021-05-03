package com.example.sweetshop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetshop.Model.Cartdata;
import com.example.sweetshop.Model.Data;
import com.example.sweetshop.Model.Orderitem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference DBref;
    private FirebaseUser user;
    private String userID;
    private static int checkoutprice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        checkoutprice = 0;

        DBref = FirebaseDatabase.getInstance().getReference().child("OrderHistory").child(userID);
        DBref.keepSynced(true);

        recyclerView =findViewById(R.id.recyclerOrderHistory);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Orderitem, OHistoryviewHolder> adapter = new FirebaseRecyclerAdapter<Orderitem, OHistoryviewHolder>(
                Orderitem.class,
                R.layout.cus_item_data_3,
                OHistoryviewHolder.class,
                DBref
        ) {

            @Override
            protected void populateViewHolder(OHistoryviewHolder historyviewHolder, final Orderitem orderitem, int i) {
                historyviewHolder.setTitle(orderitem.getTitle());
                historyviewHolder.setPrice(orderitem.getPrice());
                historyviewHolder.setQuantity(orderitem.getQuantity());
                historyviewHolder.setTotal(orderitem.getPrice(),orderitem.getQuantity());
                historyviewHolder.setImage(orderitem.getImage());
                historyviewHolder.setDateandTime(orderitem.getDate(),orderitem.getTime());



            }
        };


        recyclerView.setAdapter(adapter);
    }

    public static class OHistoryviewHolder extends RecyclerView.ViewHolder{

        View mview;
        public OHistoryviewHolder(@NonNull View itemView) {
            super(itemView);
            mview =itemView;
        }

        public void setTitle(String title){
            TextView mytitle=mview.findViewById(R.id.post_title);
            mytitle.setText(title);
        }

        public void setPrice(int price){
            TextView myprice=mview.findViewById(R.id.post_price);
            myprice.setText(Integer.toString(price));
        }

        public void setQuantity(int quantity){
            TextView myquantity=mview.findViewById(R.id.post_quantity);
            myquantity.setText(Integer.toString(quantity));
        }

        public void setTotal(int price,int quantity){
            TextView mytotal=mview.findViewById(R.id.post_Total);
            int total = price * quantity;
            String str_total = Integer.toString(total);
            mytotal.setText(str_total + "/-");
        }

        public void setDateandTime(String date,String time){
            TextView TVDateandTime = mview.findViewById(R.id.TVdateandtime);

            String[] lineSplitted = date.split("-");

            String time2 = time.substring(0,time.length() - 7);
            TVDateandTime.setText("Ordered on   " +lineSplitted[2]+"-"+lineSplitted[1]+"-"+lineSplitted[0]+ "   " + time2);
        }

        public void setImage(final String image){
            final ImageView myimage=mview.findViewById(R.id.post_image);
            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(myimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(image).into(myimage) ;
                }
            });
        }
    }
}