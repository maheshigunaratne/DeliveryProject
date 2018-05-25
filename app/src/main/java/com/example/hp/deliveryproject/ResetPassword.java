package com.example.hp.deliveryproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.sun.mail.smtp.SMTPTransport;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Model.User;



public class ResetPassword extends AppCompatActivity {
    Button reset;
    private EditText et_username;
    private String username, Subject, textMsg, ToEmail, FromEmail, Passs;
    Session session = null;
    ProgressDialog pddalog = null;
    Context context = null;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recoverpassword);
        reset = (Button) findViewById(R.id.recover);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_username = (EditText) findViewById(R.id.emailTXT);
                username = et_username.getText().toString();
                if (username.equals("")) {
                    Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Enter username to Reset", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference rootRef = database.getReference("tables");
                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("users").hasChild(et_username.getText().toString())) {

                                Properties prop = new Properties();
                                prop.put("mail.smtp.host", "smtp.gmail.com");
                                prop.put("mail.smtp.socketFoctoty.port", "465");
                                prop.put("mail.smtp.socketFoctoty.class", " javax.net.ssl.SSLSocketFactory");
                                prop.put("mail.smtp.auth", "true");
                                prop.put("mail.smtp.port", "465");

                                session = Session.getInstance(prop, new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication("deliveritmad@gmail.com", "deliveritmad123");
                                    }
                                });

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("tables/users/"+ username);
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        pddalog = new ProgressDialog(ResetPassword.this);
                                        pddalog.setMessage("sending Mail...");
                                        pddalog.show();


                                        RetreiveFeedTask task = new RetreiveFeedTask(user);
                                        task.execute();


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Invalid username", Snackbar.LENGTH_SHORT);
                                        mySnackbar.show();
                                    }
                                });




                                //Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "User Already Available", Snackbar.LENGTH_SHORT);
                                //mySnackbar.show();
                            } else {
                                Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Invalid username", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();


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

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        User user;

        public RetreiveFeedTask(User user){
            this.user = user;
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                System.out.println("EMAIL" + user.getEmail() + user.getPassword());
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("deliveritmad@gmail.com"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
                message.setSubject("DeliverIT Password Recovery");
                message.setContent("Your password is: "+user.getPassword(), "text/html; charset=utf-8");
                SMTPTransport st = (SMTPTransport) session.getTransport("smtps");
                st.connect("smtp.gmail.com","deliveritmad@gmail.com", "deliveritmad123");
                st.sendMessage(message, message.getAllRecipients());
                //Transport.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private void sendEmail(User user) {


        }


        @Override
        protected void onPostExecute(String s) {
            pddalog.dismiss();
            Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Email sent ,Check Your Email", Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }


    }


}


