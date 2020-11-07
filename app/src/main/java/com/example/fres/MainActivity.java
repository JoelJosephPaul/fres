package com.example.fres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView udet;
    private Button shb,adb,rb,increq,pendbut,rentedbut,lout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        udet = findViewById(R.id.userdetails);
        shb=findViewById(R.id.showbikebut);
        adb=findViewById(R.id.addbikebut);
        rb=findViewById(R.id.rentbikebut);
        increq=findViewById(R.id.incomingrequests);
        pendbut=findViewById(R.id.pendingbut);
        rentedbut=findViewById(R.id.myrentedbut);
        lout=findViewById(R.id.logout);
        Bundle bundle = getIntent().getExtras();
        final String u = bundle.getString("username");
        udet.setText("Welcome "+u);
        adb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adbike = new Intent(MainActivity.this,addbike.class);
                adbike.putExtra("username",u);
                startActivity(adbike);
            }
        });
        shb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shbike = new Intent(MainActivity.this,mybikes.class);
                shbike.putExtra("username",u);
                startActivity(shbike);
            }
        });
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rbike = new Intent(MainActivity.this,rentabike.class);
                rbike.putExtra("username",u);
                startActivity(rbike);
            }
        });
        increq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inreqbike = new Intent(MainActivity.this,incomingrequests.class);
                inreqbike.putExtra("username",u);
                startActivity(inreqbike);
            }
        });
        pendbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pend = new Intent(MainActivity.this,pendingrequests.class);
                pend.putExtra("username",u);
                startActivity(pend);
            }
        });
        rentedbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rented = new Intent(MainActivity.this,myrented.class);
                rented.putExtra("username",u);
                startActivity(rented);
            }
        });
        /*
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(MainActivity.this,login.class);
                startActivity(log);
            }
        });*/
    }
    @Override
    public void onBackPressed() {
// do something on back.
        //Toast.makeText(MainActivity.this, "m backing", Toast.LENGTH_LONG).show();
        MainActivity.this.finish();
        Toast.makeText(MainActivity.this, "m backing", Toast.LENGTH_LONG).show();
        return;
    }
}
