package com.example.sweetshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sweetshop.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class DashboardFragment2 extends Fragment {

    private RecyclerView recyclerView;

    //Firebase..

    private DatabaseReference mDashboard2Database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myview = inflater.inflate(R.layout.fragment_dashboard2, container, false);

        mDashboard2Database = FirebaseDatabase.getInstance().getReference().child("Products");
        mDashboard2Database.keepSynced(true);

        recyclerView =myview.findViewById(R.id.recyclerDashboard2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager) ;

        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data,Dashboard2viewHolder>adapter = new FirebaseRecyclerAdapter<Data, Dashboard2viewHolder>(
                Data.class,
                R.layout.cus_item_data,
                Dashboard2viewHolder.class,
                mDashboard2Database
        ) {
            @Override
            protected void populateViewHolder(Dashboard2viewHolder dashboard2viewHolder, final Data data, final int i) {
                dashboard2viewHolder.setTitle(data.getTitle());
                //dashboard2viewHolder.setDescription(data.getDescription());
                dashboard2viewHolder.setPrice(data.getPrice());
                dashboard2viewHolder.setImage(data.getImage());

                dashboard2viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),CatOneDetails.class);
                        intent.putExtra("title",data.getTitle());
                        intent.putExtra("description",data.getDescription());
                        intent.putExtra("price",Integer.toString(data.getPrice()));
                        intent.putExtra("image",data.getImage());
                        intent.putExtra("id",data.getId());
                        intent.putExtra("total_quantity",Integer.toString(data.getTotal_quantity()));

                        startActivity(intent);
                    }
                });

            }
        };

        recyclerView.setAdapter(adapter);
    }

    public static class Dashboard2viewHolder extends RecyclerView.ViewHolder{

        View mview;
        public Dashboard2viewHolder(@NonNull View itemView) {
            super(itemView);
            mview =itemView;
        }

        public void setTitle(String title){
            TextView mytitle=mview.findViewById(R.id.post_title);
            mytitle.setText(title);
        }

        public void setDescription(String description){
            TextView mydescription=mview.findViewById(R.id.post_description);
            mydescription.setText(description);
        }

        public void setPrice(int price){
            TextView myprice=mview.findViewById(R.id.post_price);
            myprice.setText(Integer.toString(price) + "/- per Kg");
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
