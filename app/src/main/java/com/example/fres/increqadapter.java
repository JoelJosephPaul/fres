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

import java.util.ArrayList;

public class increqadapter extends ArrayAdapter<Member> {
    Context ApplicationContext;
    ArrayList<Member> incblist;
    public increqadapter(Context applicationContext,int resource,ArrayList<Member> objects)
    {
        super(applicationContext, resource, objects);
        this.ApplicationContext = applicationContext;
        this.incblist=objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //View view = LayoutInflater.from(ApplicationContext).inflate(R.layout.bikeviewlayout,null);
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.increntviewlayout, null);

        TextView reqbikeid = (TextView)v.findViewById(R.id.reqbikeid);
        TextView  seekername = (TextView)v.findViewById(R.id.seekername);
        TextView seekernum = (TextView)v.findViewById(R.id.seekernum);
        ImageView personimage = (ImageView) v.findViewById(R.id.personimage);

        reqbikeid.setText(incblist.get(position).getPassword());//password is bikeid
        seekername.setText(incblist.get(position).getName());
        //seekernum.setText(incblist.get(position));
        personimage.setImageResource(R.drawable.ic_launcher_foreground);
        return v;
    }
}
