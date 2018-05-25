package com.example.hp.deliveryproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.User;

public class MainActivity extends AppCompatActivity {
    Button regBtn, loginBtn, resetPw;
    EditText textPassword, textEmail;
    SharedPreferences svUserName, svPassword, svUserType;
    CheckBox chkSaveData;
    String textUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regBtn = (Button) findViewById(R.id.registerBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        resetPw = (Button) findViewById(R.id.reset);
        textPassword = (EditText) findViewById((R.id.textPassword));
        textEmail = (EditText) findViewById(R.id.textEmailAddress);
        //Bhagya- References Regarding Sessions
        svUserName = getSharedPreferences("MYPREFS", 0);
        svPassword = getSharedPreferences("MYPREFS2", 0);
        svUserType = getSharedPreferences("MYPREFS3", 0);
        textEmail.setText(svUserName.getString("email", ""));
        textPassword.setText(svPassword.getString("pwrd", ""));
        textUserType = "HEY THERE I'M A NORMAL";
        chkSaveData = (CheckBox) findViewById(R.id.chkKeepLogDetails);
        //Bhagya- End of References Regarding Sessions
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registrationIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        });
        resetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(registrationIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textEmail.getText().toString().equals("") || textEmail.getText().toString() == null) {
                    Toast noUsernameToast = Toast.makeText(MainActivity.this, "Please enter username!", Toast.LENGTH_SHORT);
                    noUsernameToast.setGravity(Gravity.CENTER, 0, 0);
                    noUsernameToast.show();
                } else {
                    Toast loginToast = Toast.makeText(MainActivity.this, "We Are Logging you in Please Wait...!", Toast.LENGTH_SHORT);
                    loginToast.setGravity(Gravity.CENTER, 0, 0);
                    loginToast.show();

                    SharedPreferences.Editor unameEditor = svUserName.edit();
                    SharedPreferences.Editor pwrdEditor = svPassword.edit();
                    SharedPreferences.Editor uTypeEditor = svUserType.edit();
                    if (chkSaveData.isEnabled() == true) {
                        //Bhagya- Sessions
                        Log.i("" + chkSaveData.isEnabled(), "" + chkSaveData.isEnabled());
                        svUserName = getSharedPreferences("MYPREFS", 0);
                        unameEditor.putString("email", textEmail.getText().toString());
                        svPassword = getSharedPreferences("MYPREFS2", 0);
                        pwrdEditor.putString("pwrd", textPassword.getText().toString());
                        svUserType = getSharedPreferences("MYPREFS3", 0);
                        uTypeEditor.putString("uType", textUserType);

                        unameEditor.commit();
                        pwrdEditor.commit();
                        uTypeEditor.commit();
                        Log.i("USER TYPE", textUserType);
                        //Bhagya- End of Sessions
                    } else {
                        //Bhagya- Invalidate Sessions
                        unameEditor.clear();
                        unameEditor.commit();
                        pwrdEditor.clear();
                        pwrdEditor.commit();
                        //Bhagya- End of Invalidating Sessions
                    }
                    try {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("tables");
                        myRef = myRef.child("users");

                        myRef = myRef.child(textEmail.getText().toString());
                        Log.i("MEkala", myRef.toString());
                        if (myRef == null)
                            loginStatus(false, "");
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    User user = dataSnapshot.getValue(User.class);
                                    if ((textPassword.getText().toString().equals(user.getPassword()))) {
                                        //Bhagya- Login Message;
                                        Toast loginToast = Toast.makeText(MainActivity.this, "Log In Successful...!", Toast.LENGTH_SHORT);
                                        loginToast.setGravity(Gravity.CENTER, 0, 0);
                                        loginToast.show();
                                        loginStatus(true, user.getUserType().toString());
                                        //Bhagya - End of Login Message
                                    } else if ((!textPassword.getText().toString().equals(user.getPassword())) || (!textEmail.getText().toString().equals(user.getEmail()))) {
                                        Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Invalid Username or Password", Snackbar.LENGTH_LONG);
                                        mySnackbar.show();
                                    } else {
                                        //Bhagya- Password Error Message;
                                        Toast loginToast = Toast.makeText(MainActivity.this, "Looks Like Something Went Wrong!", Toast.LENGTH_SHORT);
                                        loginToast.setGravity(Gravity.CENTER, 0, 0);
                                        loginToast.show();
                                        //Bhagya- End of Password Error Message;
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                    recreate();
                                    Toast noUsernameToast = Toast.makeText(MainActivity.this, "Try again. Error occurred!", Toast.LENGTH_SHORT);
                                    noUsernameToast.setGravity(Gravity.CENTER, 0, 0);
                                    noUsernameToast.show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast noUsernameToast = Toast.makeText(MainActivity.this, "Try again. Error occurred!", Toast.LENGTH_SHORT);
                        noUsernameToast.setGravity(Gravity.CENTER, 0, 0);
                        noUsernameToast.show();
                    }

                }


            }
        });


        // Write a message to the database

        /*User user = new User();
        user.setUsername("murtazasmart");
        user.setPassword("hello");

        myRef.setValue(user);

        User usertra;

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User trialuser = dataSnapshot.getValue(User.class);
                System.out.println(trialuser.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        //TEST COMMIT BHAGYA
        //testing 123 kasun :D
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void loginStatus(boolean loggedIn, String Utype) {
        System.out.println("EMAIL  - " + textEmail.getText().toString());
        if (loggedIn && !Utype.equals("")) {
            if (Utype.equals("Customer")) {
                System.out.println("LOGGED IN " + textEmail.getText().toString());
                Intent userDashboard = new Intent(MainActivity.this, UserDashboard.class);//Intent userDashboard = new Intent(MainActivity.this, UserDashboard.class );
                startActivity(userDashboard);
            } else if (Utype.equals("Manager")) {
                System.out.println("LOGGED IN " + textEmail.getText().toString());
                Intent managerDashboard = new Intent(MainActivity.this, ManagerDashboard.class);
                startActivity(managerDashboard);

            } else if (Utype.equals("agent")) {
                System.out.println("LOGGED IN " + textEmail.getText().toString());
                Intent deliveryAgentDashboard = new Intent(MainActivity.this, DeliveryAgentDashboard.class);
                startActivity(deliveryAgentDashboard);

            } else {
                //else{
                //System.out.println("LOGGED IN "+textEmail.getText().toString());
                //Intent userDashboard = new Intent(MainActivity.this, MainActivity.class);
                //  startActivity(userDashboard);

                System.out.println("ERROR LOGGIN" + textEmail.getText().toString());
            }

        } else {
            System.out.println("ERROR LOGGIN" + textEmail.getText().toString());
        }

    }
}
