package com.example.hp.deliveryproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Controller.DeliveryAgentViewPickupListController;
import Controller.ManagerViewDeliveryAgentController;
import Model.DeliveryDetails;
import Model.User;


public class ManagerViewDeliveryAgent extends AppCompatActivity {

    private List<User> managerViewDeliveryAgentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ManagerViewDeliveryAgentController mAdapter;
    private DatabaseReference databaseReference;
    private ImageButton addNewAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerviewdeliveryagent);

        recyclerView = (RecyclerView) findViewById(R.id.manager_delivery_agent_recycler_view);

        mAdapter = new ManagerViewDeliveryAgentController(managerViewDeliveryAgentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        addNewAgent = (ImageButton) findViewById(R.id.btnAddNewAgent);

        addNewAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerViewDeliveryAgent.this, ManagerAddDeliveryPerson.class);
                startActivity(intent);
            }
        });


        //CONNECT TO DATABASE AND THEN SET RECYLER VIEW ADAPTER
        databaseReference = FirebaseDatabase.getInstance().getReference("tables/users");
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
            if(dataSnapshotItem.getValue(User.class).getUserType().equals("agent"))
                managerViewDeliveryAgentList.add(dataSnapshotItem.getValue(User.class));
            mAdapter = new ManagerViewDeliveryAgentController(managerViewDeliveryAgentList);
            recyclerView.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

}
