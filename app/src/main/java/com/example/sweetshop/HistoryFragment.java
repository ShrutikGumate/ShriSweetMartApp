package com.example.sweetshop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetshop.Model.Cartdata;
import com.example.sweetshop.Model.Data;
import com.example.sweetshop.Model.Orderitem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class HistoryFragment extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference DBref,DBref2,DBref3,databaseReference;
    private FirebaseUser user;
    private String userID;
    private TextView TVcheckoutprice;
    private static int checkoutprice=0;
    private Button btnCheckOut;
    private ArrayList<Data> datalist = new ArrayList<Data>();
    private ArrayList<Cartdata> cartitems = new ArrayList<Cartdata>();
    private ArrayList<Integer> cartitemquantity = new ArrayList<Integer>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myview = inflater.inflate(R.layout.fragment_history, container, false);

        btnCheckOut = myview.findViewById(R.id.buttonCheckOut);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        checkoutprice = 0;


        DBref = FirebaseDatabase.getInstance().getReference().child("CartDB").child(userID);
        DBref.keepSynced(true);

        recyclerView =myview.findViewById(R.id.recyclerCart);
        TVcheckoutprice=myview.findViewById(R.id.checkoutprice);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                int i = 0;

                if(HistoryFragment.checkoutprice > 0) {

                    DBref2 = FirebaseDatabase.getInstance().getReference("Products");
                    DBref3 = FirebaseDatabase.getInstance().getReference("OrderHistory");
                    for (Cartdata cartitem : cartitems) {


                        HashMap hashMap = new HashMap();
                        int x = cartitem.getTotal_quantity() - cartitem.getQuantity();
                        hashMap.put("total_quantity", x);
                        DBref2.child(cartitem.getProduct_id()).updateChildren(hashMap);

                        String date = String.valueOf(java.time.LocalDate.now());
                        String time = String.valueOf(java.time.LocalTime.now());
                        HashMap hashMap2 = new HashMap();
                        hashMap2.put("date",date);
                        hashMap2.put("time",time);

                        String id = DBref3.push().getKey();

                        Orderitem orderitem = new Orderitem(id,cartitem.getProduct_id(),cartitem.getTitle(),cartitem.getImage(),cartitem.getPrice(),cartitem.getQuantity(),date,time);

                        DBref3.child(userID).child(id).setValue(orderitem);
                        DBref3.child(userID).child(id).updateChildren(hashMap2);

                    }
                    databaseReference = FirebaseDatabase.getInstance().getReference("CartDB").child(userID);
                    databaseReference.removeValue();

                    Toast.makeText(myview.getContext(), "Checkout Successfully!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(myview.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(myview.getContext(), "Please add at least 1 item!", Toast.LENGTH_LONG).show();
                }

            }
        });



        return myview;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Cartdata,HistoryviewHolder>adapter = new FirebaseRecyclerAdapter<Cartdata, HistoryviewHolder>(
                Cartdata.class,
                R.layout.cus_item_data_2,
                HistoryviewHolder.class,
                DBref
        ) {
            @Override
            protected void populateViewHolder(HistoryviewHolder historyviewHolder, final Cartdata cartdata, int i) {
                historyviewHolder.setTitle(cartdata.getTitle());
                historyviewHolder.setPrice(cartdata.getPrice());
                historyviewHolder.setQuantity(cartdata.getQuantity());
                historyviewHolder.setTotal(cartdata.getPrice(),cartdata.getQuantity());
                TVcheckoutprice.setText(Integer.toString(HistoryFragment.checkoutprice) + "/-");
                historyviewHolder.setImage(cartdata.getImage());

                cartitems.add(cartdata);

                historyviewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(),CatTwoDetails.class);
                        intent.putExtra("title",cartdata.getTitle());
                        intent.putExtra("price",Integer.toString(cartdata.getPrice()));
                        intent.putExtra("image",cartdata.getImage());
                        intent.putExtra("quantity",Integer.toString(cartdata.getQuantity()));
                        intent.putExtra("product_id",cartdata.getProduct_id());
                        intent.putExtra("userID",userID);
                        startActivity(intent);
                    }
                });



            }
        };


        recyclerView.setAdapter(adapter);
    }

    public static class HistoryviewHolder extends RecyclerView.ViewHolder{

        View mview;
        public HistoryviewHolder(@NonNull View itemView) {
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
            HistoryFragment.checkoutprice += total;
            String str_total = Integer.toString(total);
            mytotal.setText(str_total + "/-");
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