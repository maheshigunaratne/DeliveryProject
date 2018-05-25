package com.example.hp.deliveryproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import Controller.OrdersListController;
import Model.DeliveryDetails;
import Model.User;



public class ManagerDashboard extends AppCompatActivity {

    ImageButton btnManageDelAgent,btnViewOrders,btnChangePw;
    Button logOut;
    //OrdersListController ordersController = new OrdersListController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerdashboard);

        btnManageDelAgent = (ImageButton) findViewById(R.id.btnManageDelAgent);
        btnManageDelAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageDelAgent = new Intent(ManagerDashboard.this,ManagerViewDeliveryAgent.class);
                startActivity(manageDelAgent);
            }
        });

        btnViewOrders = (ImageButton) findViewById(R.id.btnViewOrders);
        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt= new Intent(ManagerDashboard.this,ManagerViewPickup.class);
                startActivity(intt);
            }
        });

        btnChangePw = (ImageButton) findViewById(R.id.btnChangePw);
        btnChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(ManagerDashboard.this,ChangePassword.class);
                startActivity(intt);
            }
        });

        logOut= (Button) findViewById(R.id.buttonlogout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(ManagerDashboard.this,MainActivity.class);
                startActivity(intt);
                finish();
            }
        });

    }

    private void collectPhoneNumbers(Map<String,Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("fromLocation"));
        }

        System.out.println("NUMBER OF USERS:"+phoneNumbers.size());
        for(int i=0;i<phoneNumbers.size();i++)
        {
            System.out.println("USER EMAIL: "+phoneNumbers.get(i));
        }
    }
}
