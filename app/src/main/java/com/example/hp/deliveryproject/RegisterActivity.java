package com.example.hp.deliveryproject;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.User;

public class RegisterActivity extends AppCompatActivity{
    private EditText et_name,et_email, et_password, et_confirmpassword, et_phone  ;
    private String name,Email,password,confirmpassword;
    Button regbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = (EditText) findViewById(R.id.name);
        et_email = (EditText) findViewById(R.id.Email);
        et_password = (EditText) findViewById(R.id.Password);
        et_confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        et_phone = (EditText) findViewById(R.id.phone);
        regbtn = (Button) findViewById(R.id.registbtn);
        regbtn.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onClick(View v){
                /*DatabaseReference myRef = database.getReference("tables");
                myRef = myRef.child("users");
                myRef = myRef.child(textEmail.getText().toString());
                if(myRef == null)
                finish();*/
                //finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                //User userReg =  new User(et_name.getText().toString(),et_confirmpassword.getText().toString(),true,et_email.getText().toString(),et_phone.getText().toString());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //DatabaseReference myRef = database.getReference("tables");


                //myRef = myRef.child("users");

                DatabaseReference rootRef =database.getReference("tables");
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("users").hasChild(et_name.getText().toString())){
                            //et_name.setText("fuck");
                            Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "User Already Available", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                        }else{
                            if (et_password.getText().toString().equals(et_confirmpassword.getText().toString())){
                User userReg =  new User(et_name.getText().toString(),et_confirmpassword.getText().toString(),true,et_email.getText().toString(),et_phone.getText().toString(), "Customer".toString());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("tables");

                myRef = myRef.child("users");
                myRef = myRef.child(userReg.getName().toString());
                myRef.setValue(userReg);
                            finish();//mekala
                        }

                        else{
                                Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Please Re-Conferm Ther Password", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            }
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

