package com.example.fres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class bikelistadapter extends ArrayAdapter<Bike> {

    //private static final String TAG = "mybikes";
    Context ApplicationContext;
    //ArrayList<Bike> blist = new ArrayList<>();
    ArrayList<Bike> blist ;
    int rented;

    public bikelistadapter(Context applicationContext,int resource,ArrayList<Bike> objects,int rented) {
        super(applicationContext, resource, objects);
        this.ApplicationContext = applicationContext;
        this.blist=objects;
        this.rented = rented;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //View view = LayoutInflater.from(ApplicationContext).inflate(R.layout.bikeviewlayout,null);
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.bikeviewlayout, null);
        //Toast.makeText(ApplicationContext, "poliuy", Toast.LENGTH_SHORT).show();
        //Log.d(TAG,blist.get(position).getBikeid() );

        TextView blistbikeid =(TextView) v.findViewById(R.id.blistbikeid);
        TextView blistbrand =(TextView) v.findViewById(R.id.blistbrand);
        TextView blistmodel =(TextView) v.findViewById(R.id.blistmodel);
        TextView blistcc =(TextView) v.findViewById(R.id.blistcc);
        TextView blistyop =(TextView) v.findViewById(R.id.blistyop);
        TextView blistextdet =(TextView) v.findViewById(R.id.blistextdet);
        final TextView blistavail = (TextView) v.findViewById(R.id.blistavail);
        final TextView blistrented = (TextView) v.findViewById(R.id.blistrented);
        ImageView im =(ImageView) v.findViewById(R.id.bikeimage);

        //TextView jk = v.findViewById(R.id.listofbikes);
        blistbikeid.setText(blist.get(position).getBikeid());
        blistbrand.setText(blist.get(position).getBrand());
        blistmodel.setText(blist.get(position).getModel());
        blistcc.setText(Integer.toString(blist.get(position).getCc()));
        blistyop.setText(Integer.toString(blist.get(position).getYop()));
        blistextdet.setText(blist.get(position).getExtdet());
        if(rented==0) {
            blistavail.setText(blist.get(position).getAvail() == 1 ? "Available" : "Not Available");
            blistrented.setText(blist.get(position).getRented() == 1 ? "Rented" : "Not Rented");
        }
        else
        {
            DatabaseReference bref = FirebaseDatabase.getInstance().getReference().child("Bikedata");
            bref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    blistavail.setText(dataSnapshot.child(blist.get(position).getBikeid()).child("name").getValue().toString());
                    blistrented.setText(dataSnapshot.child(blist.get(position).getBikeid()).child("number").getValue().toString());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        im.setImageResource(R.drawable.ic_launcher_background);
        //jk.setText(Integer.toString(blist.size()));
        return v;
    }
}
