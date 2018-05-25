package com.example.hp.deliveryproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Controller.OrdersListController;
import Controller.UserViewPickupController;
import Model.DeliveryDetails;



public class ManagerViewPickup extends AppCompatActivity {

    private List<DeliveryDetails> managerViewPickupList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrdersListController mAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerviewpickup);

        recyclerView = (RecyclerView) findViewById(R.id.manager_pickup_recycler_view);

        mAdapter = new OrdersListController(managerViewPickupList);
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
            managerViewPickupList.add(dataSnapshotItem.getValue(DeliveryDetails.class));
            mAdapter = new OrdersListController(managerViewPickupList);
            recyclerView.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

}
