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

    TextView listofbikes;
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

        listofbikes=findViewById(R.id.listofbikes);

        bikelistview = findViewById(R.id.bikelistview);

        getbikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata");

        getbikerefflistener = getbikereff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                blist.clear();
                //Toast.makeText(mybikes.this, "Total user  : "+ dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                //listofbikes.setText("Total user  : "+ dataSnapshot.getChildrenCount());
                //listofbikes.setText(blist.size());
                //listofbikes.setText(";;;;"+u+";;;;;");
                //ong cou = dataSnapshot.getChildrenCount();
                while (items.hasNext())
                {
                    DataSnapshot item = items.next();
                    //Toast.makeText(mybikes.this, item.child("username").toString(), Toast.LENGTH_LONG).show();
                    //listofbikes.setText(listofbikes.getText().toString()+",,"+item.child("username").getValue().toString());
                    Bike b = new Bike();
                    if(item.child("username").getValue().toString().equals(u))
                    {
                        //m++;
                        //listofbikes.setText(listofbikes.getText().toString()+",,"+Integer.toString(m));
                        //listofbikes.setText(item.child("username").getValue().toString());
                        //listofbikes.setText(listofbikes.getText().toString()+item.child("bikeid").getValue().toString()+item.child("brand").getValue().toString()+item.child("model").getValue().toString()+item.child("cc").getValue().toString()+item.child("yop").getValue().toString()+item.child("extdet").getValue().toString());
                        //
                        //b = new Bike();
                        b.setBikeid(item.child("bikeid").getValue().toString());
                        b.setUsername(item.child("username").getValue().toString());
                        b.setBrand(item.child("brand").getValue().toString());
                        b.setModel(item.child("model").getValue().toString());
                        b.setCc(Integer.parseInt(item.child("cc").getValue().toString()));
                        b.setYop(Integer.parseInt(item.child("yop").getValue().toString()));
                        b.setExtdet(item.child("extdet").getValue().toString());
                        b.setAvail(Integer.parseInt(item.child("avail").getValue().toString()));
                        b.setRented(Integer.parseInt(item.child("rented").getValue().toString()));
                        //Toast.makeText(mybikes.this, item.child("avail").getValue().toString(), Toast.LENGTH_SHORT).show();
                        blist.add(b);
                        //listofbikes.setText(Integer.toString(blist.size()));
                        //listofbikes.setText(blist.get(0).getBikeid());
                        //kiranow    //   listofbikes.setText(blist.toString());
                        //qqqq.setText(blist.toString());
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
                //  kira now// listcont=0;
                bname = blist.get(position).getBikeid();
                tempbikereff = FirebaseDatabase.getInstance().getReference().child("Bikedata").child(bname);
                //tempbikereff.setValue(tempbikereff);
                //Toast.makeText(mybikes.this, tempbikereff.toString(), Toast.LENGTH_SHORT).show();
                //int x = tempbikereff.child(bname)
                // kiranow cont=1;

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
