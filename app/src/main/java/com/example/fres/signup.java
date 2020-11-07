package com.example.fres;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity {
    private EditText name,username,password,cpassword;
    private Button signup;
    DatabaseReference signupreff;
    Member member;
    String tname,tusname,tpass,tcpass;
    //int inserted;
    private static final String TAG = signup.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(signup.this, "ssssignnn", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.sname);
        username = findViewById(R.id.susname);
        password = findViewById(R.id.spass);
        cpassword=findViewById(R.id.scpass);
        signup = findViewById(R.id.sbutton);

        signupreff = FirebaseDatabase.getInstance().getReference().child("Member");
        //inserted=0;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                member=new Member();
                tname = name.getText().toString();
                tusname = username.getText().toString();
                tpass = password.getText().toString();
                tcpass=cpassword.getText().toString();

                if(tname.isEmpty()||tusname.isEmpty()||tpass.isEmpty()||tcpass.isEmpty()) {
                    Toast.makeText(signup.this, "incomplete fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!tpass.equals(tcpass))
                {
                    Toast.makeText(signup.this, "passwords don't match", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    member.setName(tname);
                    member.setUsername(tusname);
                    member.setPassword(tpass);

                    signupreff.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //if inserted == 0
                            if(dataSnapshot.hasChild(member.getUsername()))
                                Toast.makeText(signup.this, "Username already exists", Toast.LENGTH_SHORT).show();
                            else {
                                //inserted = 1;
                                signupreff.child(tusname).setValue(member).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(signup.this, "Sign up success", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(signup.this, "failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    /*
                                    Intent login = new Intent(signup.this,login.class);
                                    startActivity(login);*/
                                signup.this.finish();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
// do something on back.
        Toast.makeText(signup.this, "ss backing", Toast.LENGTH_LONG).show();
        signup.this.finish();
        return;
    }
}