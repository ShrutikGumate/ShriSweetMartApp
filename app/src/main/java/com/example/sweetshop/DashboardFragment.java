package com.example.sweetshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class DashboardFragment extends Fragment {
    private RecyclerView dashboardrecycler;
    private RecyclerView inforecycler;

    private DatabaseReference myHistoryDatabase;

    private DatabaseReference myInfoDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_dashboard, container, false);

        myHistoryDatabase = FirebaseDatabase.getInstance().getReference().child("HistoryDatabase");

        myInfoDatabase = FirebaseDatabase.getInstance().getReference().child("InfoDatabase");

        // History Recycler

        dashboardrecycler = myview.findViewById(R.id.recyclerdashboard);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        linearLayoutManager.setReverseLayout(true);

        linearLayoutManager.setStackFromEnd(true);

        dashboardrecycler.setHasFixedSize(true);

        dashboardrecycler.setLayoutManager(linearLayoutManager);

        // Info Recycler

        inforecycler=myview.findViewById(R.id.recyclerinfo);
        LinearLayoutManager layoutManagerinfo = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManagerinfo.setStackFromEnd(true);
        layoutManagerinfo.setReverseLayout(true);
        inforecycler.setHasFixedSize(true);
        inforecycler.setLayoutManager(layoutManagerinfo);



        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Data,HistoryViewHolder> adapterOne = new FirebaseRecyclerAdapter<Data, HistoryViewHolder>
                (
                        Data.class,
                        R.layout.item_data,
                        HistoryViewHolder.class,
                        myHistoryDatabase


                ) {
            @Override
            protected void populateViewHolder(HistoryViewHolder historyViewHolder, final Data data, int i) {
                historyViewHolder.setTitle(data.getTitle());
                historyViewHolder.setDescription(data.getDescription());
                historyViewHolder.setImage(data.getImage());

                historyViewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),CatOneDetails.class);
                        intent.putExtra("title",data.getTitle());
                        intent.putExtra("description",data.getDescription());
                        intent.putExtra("image",data.getImage());

                        startActivity(intent);
                    }
                });
            }
        };

        dashboardrecycler.setAdapter(adapterOne);

        FirebaseRecyclerAdapter<Data,InfoViewHolder> adaptertwo= new FirebaseRecyclerAdapter<Data, InfoViewHolder>(
                Data.class,
                R.layout.item_data,
                InfoViewHolder.class,
                myInfoDatabase
        ) {
            @Override
            protected void populateViewHolder(InfoViewHolder infoViewHolder, final Data data, int i) {
                infoViewHolder.msetTitle(data.getTitle());
                infoViewHolder.msetDescription(data.getDescription());
                infoViewHolder.msetimage(data.getImage());

                infoViewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),CatOneDetails.class);
                        intent.putExtra("title",data.getTitle());
                        intent.putExtra("description",data.getDescription());
                        intent.putExtra("image",data.getImage());

                        startActivity(intent);
                    }
                });
            }
        };
        inforecycler.setAdapter(adaptertwo);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{

        View myview;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            myview =itemView;
        }

        public void setTitle(String title){
            TextView mytitle=myview.findViewById(R.id.title);
            mytitle.setText(title);
        }

        public void setDescription(String description){
            TextView mydescription=myview.findViewById(R.id.description);
            mydescription.setText(description);
        }

        public void setImage(final String image){
            final ImageView myimage=myview.findViewById(R.id.imageview);
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

    public static class InfoViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void msetTitle(String title){
            TextView mytitle = myview.findViewById(R.id.title);
            mytitle.setText(title);
        }

        public void msetDescription(String description){
            TextView mydescription = myview.findViewById(R.id.description);
            mydescription.setText(description);
        }

        public void msetimage(final String image){
            final ImageView myimageview = myview.findViewById(R.id.imageview);
            Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).into(myimageview, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(image).into(myimageview);

                }
            });
        }

    }
}