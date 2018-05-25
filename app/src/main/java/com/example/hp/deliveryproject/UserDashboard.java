package com.example.hp.deliveryproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



public class UserDashboard extends AppCompatActivity {
    ImageButton viewImage, accountImage, changepasswordImage, addPickupRequest;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdashboard);
        //Access images from layout to implement action listeners
        viewImage = (ImageButton) findViewById(R.id.userdashboardimgview);
        accountImage = (ImageButton) findViewById(R.id.userdashboardimgaccount);
        changepasswordImage = (ImageButton) findViewById(R.id.btnChangeUserPassword);
        addPickupRequest = (ImageButton) findViewById(R.id.userdashboardimgaddpickuprequests);


        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderRequestsViewIntent = new Intent(UserDashboard.this, UserViewPickup.class);
                startActivity(orderRequestsViewIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        addPickupRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userAddPickupRequestIntent = new Intent(UserDashboard.this, UserAddPickupRequest.class);
                startActivity(userAddPickupRequestIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        changepasswordImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePassword = new Intent(UserDashboard.this,ChangePassword.class);
                startActivity(changePassword);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(UserDashboard.this,UpdateUserAcoountActivity.class);
                startActivity(intt);
            }
        });

        logOut = (Button) findViewById(R.id.button2);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(UserDashboard.this,MainActivity.class);
                startActivity(intt);
                finish();
            }
        });

    }
}
