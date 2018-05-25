package com.example.hp.deliveryproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Controller.DeliveryAgentViewPickupListController;
import Controller.UserViewPickupController;
import Model.DeliveryDetails;



public class DeliveryAgentViewPickup extends AppCompatActivity {

    private List<DeliveryDetails> deliveryAgentViewPickupList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeliveryAgentViewPickupListController mAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliveryagentviewpickup);

        recyclerView = (RecyclerView) findViewById(R.id.delivery_agent_recycler_view);

        mAdapter = new DeliveryAgentViewPickupListController(deliveryAgentViewPickupList);
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


    }

    private void prepareDeliveryDetailsData(DataSnapshot dataSnapshot) {
        for(DataSnapshot dataSnapshotItem : dataSnapshot.getChildren()){
            if(dataSnapshotItem.getValue(DeliveryDetails.class).getStatus().equals("assigned") && dataSnapshotItem.getValue(DeliveryDetails.class).getDeliveryAgentID().equals(getSharedPreferences("MYPREFS",0).getString("email","")))
                deliveryAgentViewPickupList.add(dataSnapshotItem.getValue(DeliveryDetails.class));
            mAdapter = new DeliveryAgentViewPickupListController(deliveryAgentViewPickupList);
            recyclerView.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }
}
