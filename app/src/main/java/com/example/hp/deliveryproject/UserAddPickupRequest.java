package com.example.hp.deliveryproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.Iterator;

import Model.DeliveryDetails;
import Model.Packages;
import Model.User;


public class UserAddPickupRequest extends AppCompatActivity implements View.OnFocusChangeListener{

    EditText lengthText, widthText, heightText;
    BigDecimal volume, price, length, width, height;
    TextView priceDisplay;
    Button submit;
    EditText deliveryDateText, toLocationText, fromLocationText, phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useraddpickuprequest);

        lengthText = (EditText) findViewById(R.id.userAddPickupRequestLength);
        widthText = (EditText) findViewById(R.id.userAddPickupRequestWidth);
        heightText = (EditText) findViewById(R.id.userAddPickupRequestHeight);
        priceDisplay = (TextView) findViewById(R.id.userAddPickupRequestPrice);
        submit = (Button) findViewById(R.id.userAddPickupRequestSubmit);
        deliveryDateText = (EditText) findViewById(R.id.userAddPickupRequestDeliveryDate);
        toLocationText = (EditText) findViewById(R.id.userAddPickupRequestToLocation);
        fromLocationText = (EditText) findViewById(R.id.userAddPickupRequestFromLocation);
        phoneText = (EditText) findViewById(R.id.phone);

        lengthText.setOnFocusChangeListener(this);
        widthText.setOnFocusChangeListener(this);
        heightText.setOnFocusChangeListener(this);

        price = new BigDecimal("45");
        volume = new BigDecimal("235.0");
        /*
        Packages pack = new Packages("1",volume.toString(),"",price.toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tables/packages");
        myRef.setValue(pack);*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tables/deliverydetails");
                ref.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int lastDeliveryID=0;
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            lastDeliveryID = Integer.parseInt(dataSnapshot1.getValue(DeliveryDetails.class).getDeliveryID());
                        }
                        lastDeliveryID++;
                        System.out.println("DELIVERY ID "+lastDeliveryID);
                        DeliveryDetails deliveryDetailsItem = new DeliveryDetails(String.valueOf(lastDeliveryID),fromLocationText.getText().toString(),toLocationText.getText().toString(),"",getSharedPreferences("MYPREFS",0).getString("email",""),"pending",deliveryDateText.getText().toString(),"nodeliveryagent assigned",price.toString());
                        insertDeliveryDetails(deliveryDetailsItem);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
/*
                DeliveryDetails deliveryDetails = new DeliveryDetails("1",fromLocationText.getText().toString(),toLocationText.getText().toString(),"","mekala","pending",deliveryDateText.getText().toString(),"nodeliveryagent assigned",price.toString());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("tables/deliverydetails/1");
                myRef.setValue(deliveryDetails);
                //myRef.setValue(deliveryDetails);
                //myRef.orderByChild("userType").equalTo("Customer").addValueEventListener(new ValueEventListener() {
                myRef.orderByKey().startAt("5").limitToFirst(1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

            }
        });





    }

    private void insertDeliveryDetails(DeliveryDetails deliveryDetailsItem) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tables/deliverydetails/"+deliveryDetailsItem.getDeliveryID());
        ref.setValue(deliveryDetailsItem);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v == lengthText || v == widthText || v == heightText){
            length = new BigDecimal(lengthText.getText().toString());
            width = new BigDecimal(widthText.getText().toString());
            height = new BigDecimal(heightText.getText().toString());
            volume = new BigDecimal(length.multiply(width.multiply(height)).toString());
            priceDisplay.setText(volume.toString());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("tables/packages");
            myRef.orderByKey().startAt(volume.toString()).limitToFirst(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for( DataSnapshot datasnapshotchild: dataSnapshot.getChildren()) {
                        System.out.println("VOLUME "+volume.toString());
                        System.out.println(datasnapshotchild.getValue(Packages.class).getPrice());
                        price = new BigDecimal(datasnapshotchild.getValue(Packages.class).getPrice().toString());
                        priceDisplay.setText(price.toString());
                    }
                    //priceDisplay.setText(dataSnapshot.getValue(Packages.class).getPrice().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    public static void printDeliveryDetails(User deliveryDetails){
        System.out.println(deliveryDetails.getPassword());

    }
}
