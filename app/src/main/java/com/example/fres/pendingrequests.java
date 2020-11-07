package com.example.fres;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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
    TextView listpendreq;

    ListView pendreqlistview;

    DatabaseReference pendbikereff,bikereff;
    ArrayList<Bike> blist=new ArrayList<>();
    String bname;
    Bike b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingrequests);


        Bundle bundle = getIntent().getExtras();
        final String u = bundle.getString("username");

        listpendreq=findViewById(R.id.listpendreq);
        pendreqlistview = findViewById(R.id.pendreqlistview);

        bikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata");
        pendbikereff = FirebaseDatabase.getInstance().getReference().child("pendingrequest");
        pendbikereff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.hasChild(u))
               {
                   Iterator<DataSnapshot> items = dataSnapshot.child(u).getChildren().iterator();//why different implementation from incoming requests
                   blist.clear();
                   while (items.hasNext())
                   {
                       DataSnapshot item=items.next();
                       b=new Bike();
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
                               blist.add(b);
                               //listpendreq.setText(blist.toString());
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });
                       //blist.add(b);
                       //listpendreq.setText(blist.toString());
                   }

                   //kiranow paste
                   //kira remove2

               }
                //kir rem 3
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //kira now remove
        bikelistadapter badapt = new bikelistadapter(pendingrequests.this,R.layout.bikeviewlayout,blist,0);
        pendreqlistview.setAdapter(badapt);

    }
}
