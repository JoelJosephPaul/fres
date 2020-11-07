package com.example.fres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    private EditText username,password;
    private Button login,newacc;
    DatabaseReference loginreff;
    Member m;
    String lusname,lpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.loginusname);
        password = findViewById(R.id.loginpass);
        login = findViewById(R.id.loginbutton);
        newacc = findViewById(R.id.caccbutton);

        loginreff = FirebaseDatabase.getInstance().getReference().child("Member");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m = new Member();
                lusname = username.getText().toString();
                lpass = password.getText().toString();
                password.setText("");

                if(lusname.isEmpty()||lpass.isEmpty()) {
                    Toast.makeText(login.this, "incomplete fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    //login validation
                    loginreff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(lusname)) {
                                if (dataSnapshot.child(lusname).child("password").getValue().toString().equals(lpass)) {
                                    Intent mainpage = new Intent(login.this, MainActivity.class);
                                    mainpage.putExtra("username", lusname);
                                    startActivity(mainpage);
                                }
                                else
                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });

        newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(login.this,signup.class);
                startActivity(signup);
            }
        });
    }
    /*kiranow
    @Override
    public void onBackPressed() {
// do something on back.
        Toast.makeText(login.this, "l backing", Toast.LENGTH_LONG).show();
        login.this.finish();
        return;
    }*/
}
