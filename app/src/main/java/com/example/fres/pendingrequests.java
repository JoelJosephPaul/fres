package com.example.fres;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class pendingrequests extends AppCompatActivity {

    ListView pendreqlistview;

    DatabaseReference pendbikereff, bikereff,increqbikereff;
    ValueEventListener pendbikerefflistener;
    ArrayList<Bike> blist = new ArrayList<>();
    String bname,mname;
    Bike b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingrequests);


        Bundle bundle = getIntent().getExtras();
        final String u = bundle.getString("username");

        pendreqlistview = findViewById(R.id.pendreqlistview);

        bikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata");
        pendbikereff = FirebaseDatabase.getInstance().getReference().child("pendingrequest").child(u);
        increqbikereff = FirebaseDatabase.getInstance().getReference().child("incomingrequest");
        pendbikerefflistener = pendbikereff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();//why different implementation from incoming requests
                blist.clear();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    b = new Bike();
                    b.setBikeid(item.getKey());
                    bikereff.addListenerForSingleValueEvent(new ValueEventListener() {// will repeat so many times bad code,
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            b.setUsername(dataSnapshot.child(b.getBikeid()).child("username").getValue().toString());
                            b.setBrand(dataSnapshot.child(b.getBikeid()).child("brand").getValue().toString());
                            b.setModel(dataSnapshot.child(b.getBikeid()).child("model").getValue().toString());
                            b.setCc(Integer.parseInt(dataSnapshot.child(b.getBikeid()).child("cc").getValue().toString()));
                            b.setYop(Integer.parseInt(dataSnapshot.child(b.getBikeid()).child("yop").getValue().toString()));
                            b.setExtdet(dataSnapshot.child(b.getBikeid()).child("extdet").getValue().toString());
                            b.setAvail(Integer.parseInt(dataSnapshot.child(b.getBikeid()).child("avail").getValue().toString()));
                            b.setRented(Integer.parseInt(dataSnapshot.child(b.getBikeid()).child("rented").getValue().toString()));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    blist.add(b);
                }
                bikelistadapter badapt = new bikelistadapter(pendingrequests.this, R.layout.bikeviewlayout, blist, 0);
                pendreqlistview.setAdapter(badapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pendreqlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //bname = blist.get(position).getPassword();//bikid
                //mname = incblist.get(position).getUsername();//username

                //pendbikereff.child(mname).child(bname).removeValue();
                //increqbikereff.child(bname).child(mname).removeValue();
                bname = blist.get(position).getBikeid();
                pendbikereff.child(bname).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mname = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                pendbikereff.child(bname).removeValue();
                increqbikereff.child(mname).child(bname).removeValue();


                return false;
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pendbikereff.removeEventListener(pendbikerefflistener);
    }
}
