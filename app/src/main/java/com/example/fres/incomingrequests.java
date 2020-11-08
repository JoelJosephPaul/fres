package com.example.fres;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class incomingrequests extends AppCompatActivity {

    ListView increqlistview;
    DatabaseReference increqbikereff, pendbikereff, rentedreff, bikereff;
    ValueEventListener increqbikerefflistener;
    ArrayList<Member> incblist = new ArrayList<>();
    String obname, bname, mname;
    //Bike b = new Bike();
    //Member m = new Member();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomingrequests);

        Bundle bundle = getIntent().getExtras();
        final String u = bundle.getString("username");


        increqlistview = findViewById(R.id.increqlistview);

        increqbikereff = FirebaseDatabase.getInstance().getReference().child("incomingrequest").child(u);
        pendbikereff = FirebaseDatabase.getInstance().getReference().child("pendingrequest");
        rentedreff = FirebaseDatabase.getInstance().getReference().child("rentedbikes");
        bikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata");
        increqbikerefflistener = increqbikereff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                incblist.clear();

                //b.setBikeid(dataSnapshot.getKey());
                while (items.hasNext()) {
                    Bike b = new Bike();
                    DataSnapshot item = items.next();
                    b.setBikeid(item.getKey());
                    Iterator<DataSnapshot> items2 = item.getChildren().iterator();
                    while (items2.hasNext()) {
                        Member m = new Member();
                        DataSnapshot item2 = items2.next();
                        m.setPassword(b.getBikeid());
                        m.setName(item2.child("name").getValue().toString());
                        m.setUsername(item2.child("username").getValue().toString());
                        incblist.add(m);
                    }
                }
                increqadapter incadapt = new increqadapter(incomingrequests.this, R.layout.increntviewlayout, incblist);
                increqlistview.setAdapter(incadapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        increqlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bname = incblist.get(position).getPassword();//bikid
                mname = incblist.get(position).getUsername();//username

                pendbikereff.child(mname).child(bname).removeValue();
                increqbikereff.child(bname).child(mname).removeValue();

            }
        });


        increqlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                bname = incblist.get(position).getPassword();//bikid
                mname = incblist.get(position).getUsername();//username

                Toast.makeText(incomingrequests.this, obname, Toast.LENGTH_SHORT).show();
                bikereff.child(bname).child("rented").setValue(1);
                rentedreff.child(mname).child(bname).setValue(u);
                pendbikereff.child(mname).child(bname).removeValue();
                increqbikereff.child(bname).child(mname).removeValue();

                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        increqbikereff.removeEventListener(increqbikerefflistener);
    }
}
