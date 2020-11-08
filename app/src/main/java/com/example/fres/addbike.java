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

public class addbike extends AppCompatActivity {
    private Button addbut;
    private EditText bikid,brname,mname,cc,yop,extdet;
    DatabaseReference addbikereff;
    Bike bike;
    String bid,bname,model,edet;
    int c,y;
    int inserted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbike);
        addbut = findViewById(R.id.addbut);
        bikid=findViewById(R.id.bikid);
        brname=findViewById(R.id.brname);
        mname=findViewById(R.id.mname);
        cc=findViewById(R.id.cc);
        yop=findViewById(R.id.yop);
        extdet=findViewById(R.id.extdet);

        Bundle bundle = getIntent().getExtras();
        final String u = bundle.getString("username");

        addbikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata");
        inserted = 0;

        addbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bike = new Bike();
                bid=bikid.getText().toString();
                bname=brname.getText().toString();
                model=mname.getText().toString();
                c= Integer.parseInt(cc.getText().toString());
                y=Integer.parseInt(yop.getText().toString());
                edet=extdet.getText().toString();

                //bike.setBikeid();
                //todo validity

                bike.setBikeid(bid);
                bike.setBrand(bname);
                bike.setModel(model);
                bike.setCc(c);
                bike.setYop(y);
                bike.setExtdet(edet);
                bike.setUsername(u);
                bike.setAvail(0);
                bike.setRented(0);

                addbikereff.child(bike.getBikeid()).setValue(bike).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addbike.this, "Add bike success", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(addbike.this, "add bike failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                addbike.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
// do something on back.
        Toast.makeText(addbike.this, "addd backing", Toast.LENGTH_LONG).show();
        addbike.this.finish();
        return;
    }
}
