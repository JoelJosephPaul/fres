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

public class mybikes extends AppCompatActivity {
    TextView qqqq;
    ListView bikelistview;

    DatabaseReference getbikereff,tempbikereff;
    ValueEventListener getbikerefflistener;
    ArrayList<Bike> blist=new ArrayList<>();
    String bname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybikes);

        Bundle bundle = getIntent().getExtras();
        final String u = bundle.getString("username");

        bikelistview = findViewById(R.id.bikelistview);

        getbikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata");

        getbikerefflistener = getbikereff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                blist.clear();

                while (items.hasNext())
                {
                    DataSnapshot item = items.next();
                    Bike b = new Bike();
                    if(item.child("username").getValue().toString().equals(u))
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
                        blist.add(b);
                    }

                }


                bikelistadapter ladapt = new bikelistadapter(mybikes.this,R.layout.bikeviewlayout,blist,0);
                bikelistview.setAdapter(ladapt);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bikelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bname = blist.get(position).getBikeid();
                tempbikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata").child(bname);

                tempbikereff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(Integer.parseInt(dataSnapshot.child("rented").getValue().toString())==0)
                        {
                            //Toast.makeText(mybikes.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                            int x = Integer.parseInt(dataSnapshot.child("avail").getValue().toString());
                            tempbikereff.child("avail").setValue(x == 0 ? 1 : 0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //Toast.makeText(mybikes.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });

        //Toast.makeText(this, blist.toString(), Toast.LENGTH_LONG).show();
        //listofbikes.setText("");
        //Toast.makeText(this,blist.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getbikereff.removeEventListener(getbikerefflistener);
    }
}
