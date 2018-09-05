package com.example.maliha.old;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;

public class Main2Activity extends AppCompatActivity {

    private ImageButton b1,b3;
    private Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        b1 = (ImageButton) findViewById(R.id.btnimport); //import contact

        b2 = (Button) findViewById(R.id.btndial); //go to dialpad

        b3 = (ImageButton) findViewById(R.id.btndel); //go to dialpad

        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //import contact

            }
        });*/


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Main4Activity.class); //go to dialpad
                startActivity(i);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Main52Activity.class); //go to del
                startActivity(i);
            }
        });
    }



}
