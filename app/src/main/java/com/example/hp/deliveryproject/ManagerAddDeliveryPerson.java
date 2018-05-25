package com.example.hp.deliveryproject;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.User;



public class ManagerAddDeliveryPerson extends AppCompatActivity {

    private EditText et_name, et_email, et_password, et_phone  ;
    private String name,Email,password,phone;
    Button regbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name=(EditText) findViewById(R.id.name);
        et_email=(EditText)findViewById(R.id.Email);
        et_password=(EditText) findViewById(R.id.Password);
        et_phone=(EditText) findViewById(R.id.phone);
        regbtn=(Button) findViewById(R.id.registbtn);

        regbtn.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v){
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference rootRef =database.getReference("tables");
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("users").hasChild(et_email.getText().toString())){
                            Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Agent Already Available", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                        }else{
                            User userReg =  new User(et_name.getText().toString(),et_password.getText().toString(),true,et_email.getText().toString(),et_phone.getText().toString(), "agent".toString());
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("tables");

                            myRef = myRef.child("users");
                            myRef = myRef.child(userReg.getEmail().toString());
                            myRef.setValue(userReg);
                            finish();//mekala done
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
