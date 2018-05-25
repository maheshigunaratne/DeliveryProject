package com.example.hp.deliveryproject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Controller.UserViewPickupController;
import Model.DeliveryDetails;



public class UserViewPickup extends AppCompatActivity{
    private List<DeliveryDetails> deliveryDetailsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserViewPickupController mAdapter;
    private DatabaseReference databaseReference;
    private ImageButton userViewAddPickupRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpickuprequestsrecyclerview);

        recyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);
        userViewAddPickupRequest = (ImageButton) findViewById(R.id.userPickRequestRecyclerViewAddPickupRequest);

        mAdapter = new UserViewPickupController(deliveryDetailsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //CONNECT TO DATABASE AND THEN SET RECYLER VIEW ADAPTER
        databaseReference = FirebaseDatabase.getInstance().getReference("tables/deliverydetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prepareDeliveryDetailsData(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userViewAddPickupRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserViewPickup.this, UserAddPickupRequest.class);
                startActivity(intent);
            }
        });


    }

    private void prepareDeliveryDetailsData(DataSnapshot dataSnapshot) {
        for(DataSnapshot dataSnapshotItem : dataSnapshot.getChildren()){
            if(dataSnapshotItem.getValue(DeliveryDetails.class).getUserID().equals(getSharedPreferences("MYPREFS",0).getString("email","")))
                deliveryDetailsList.add(dataSnapshotItem.getValue(DeliveryDetails.class));
            System.out.println(getSharedPreferences("MYPREFS",0).getString("email","") +" HEREHR" );
            mAdapter = new UserViewPickupController(deliveryDetailsList);
            recyclerView.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }


}
