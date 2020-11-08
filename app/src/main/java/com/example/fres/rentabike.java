package com.example.fres;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class rentabike extends AppCompatActivity {
    ListView rentbikelistview;
    TextView listrentabike;

    DatabaseReference rentbikereff,temprentreff,dbref,dbref2,memref;
    ValueEventListener rentbikerefflistener;
    ArrayList<Bike> brlist=new ArrayList<>();
    String bname;
    Member m=new Member();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentabike);

        Bundle bundle = getIntent().getExtras();
        final String u = bundle.getString("username");

        listrentabike = findViewById(R.id.listrentabike);
        rentbikelistview = findViewById(R.id.rentbikelistview);

        rentbikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata");
        rentbikerefflistener = rentbikereff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                brlist.clear();
                while(items.hasNext())
                {
                    DataSnapshot item = items.next();
                    Bike b = new Bike();
                    if(!item.child("username").getValue().toString().equals(u))
                    {
                        if(Integer.parseInt(item.child("avail").getValue().toString())==1&&Integer.parseInt(item.child("rented").getValue().toString())==0)
                        {
                            b.setBikeid(item.child("bikeid").getValue().toString());
                            b.setUsername(item.child("username").getValue().toString());
                            b.setBrand(item.child("brand").getValue().toString());
                            b.setModel(item.child("model").getValue().toString());
                            b.setCc(Integer.parseInt(item.child("cc").getValue().toString()));
                            b.setYop(Integer.parseInt(item.child("yop").getValue().toString()));
                            b.setExtdet(item.child("extdet").getValue().toString());
                            b.setAvail(Integer.parseInt(item.child("avail").getValue().toString()));
                            b.setRented(Integer.parseInt(item.child("rented").getValue().toString()));
                            brlist.add(b);
                            //listrentabike.setText(brlist.toString());
                        }
                    }

                }


                bikelistadapter rentadapt = new bikelistadapter(rentabike.this,R.layout.bikeviewlayout,brlist,0);
                rentbikelistview.setAdapter(rentadapt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rentbikelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bname = brlist.get(position).getBikeid();
                temprentreff = FirebaseDatabase.getInstance().getReference().child("Bikedata").child(bname);
                dbref = FirebaseDatabase.getInstance().getReference().child("incomingrequest");
                dbref2= FirebaseDatabase.getInstance().getReference().child("pendingrequest").child(u);
                memref = FirebaseDatabase.getInstance().getReference().child("Member");
                temprentreff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Bike b2 = new Bike();

                        memref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                m.setUsername(dataSnapshot.child(u).child("username").getValue().toString());
                                m.setName(dataSnapshot.child(u).child("name").getValue().toString());
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        b2.setBikeid(dataSnapshot.child("bikeid").getValue().toString());
                        b2.setUsername(dataSnapshot.child("username").getValue().toString());
                        b2.setBrand(dataSnapshot.child("brand").getValue().toString());
                        b2.setModel(dataSnapshot.child("model").getValue().toString());
                        b2.setCc(Integer.parseInt(dataSnapshot.child("cc").getValue().toString()));
                        b2.setYop(Integer.parseInt(dataSnapshot.child("yop").getValue().toString()));
                        b2.setExtdet(dataSnapshot.child("extdet").getValue().toString());
                        b2.setAvail(Integer.parseInt(dataSnapshot.child("avail").getValue().toString()));

                        dbref.child(b2.getUsername()).child(b2.getBikeid()).child(u).setValue(m);
                        dbref2.child(b2.getBikeid()).setValue(b2.getAvail());// why i set getavail here??
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rentbikereff.removeEventListener(rentbikerefflistener);
    }
}
