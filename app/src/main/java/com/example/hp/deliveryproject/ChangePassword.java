package com.example.hp.deliveryproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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



public class ChangePassword extends AppCompatActivity {

    EditText old_password, new_password, conf_new_password;
    Button submit_button;
    SharedPreferences svUserName;
    String currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        submit_button = (Button) findViewById(R.id.buttonchangepassword);
        old_password = (EditText) findViewById(R.id.editText9);
        new_password = (EditText) findViewById(R.id.editText11);
        conf_new_password = (EditText) findViewById(R.id.editText13);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svUserName = getSharedPreferences("MYPREFS", 0);
                currentUser = svUserName.getString("email", "");

                //checkOldPassword(currentUser, old_password.getText().toString());
                //new_password.setText(currentUser);
                if (new_password.getText().toString().equals(conf_new_password.getText().toString())) {

                    //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    FirebaseDatabase chkDB = FirebaseDatabase.getInstance();
                    DatabaseReference chkref = chkDB.getReference("tables");
                    //chkref=chkref.child("users");
                    chkref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child("users").hasChild(currentUser)) {

                                FirebaseDatabase DBupdate = FirebaseDatabase.getInstance();
                                DatabaseReference DBupdatRef = DBupdate.getReference("tables");
                                DBupdatRef = DBupdatRef.child("users");
                                DBupdatRef = DBupdatRef.child(currentUser);
                                DBupdatRef = DBupdatRef.child("password");
                                User UserUpdatePassword = new User();
                                //UserUpdatePassword.setPassword("xx");
                                DBupdatRef.setValue(new_password.getText().toString());//(UserUpdatePassword);
                                Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Update Successfully", Snackbar.LENGTH_LONG);
                                mySnackbar.show();
                                finish();


                                //Snackbar mySnackbar1 = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), currentUser+" done", Snackbar.LENGTH_LONG);
                                //mySnackbar1.show();

                                //UserUpdatePassword.setPassword(new_password.getText().toString());

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private boolean checkOldPassword(String currentUser, String oldPassword) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tables/users/" + currentUser);
        return true;
    }
}
