package com.example.maliha.old;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
/*class Person{
    public String n;
    public String p;
    public Integer e;

    public Person() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Person(String n, String p, Integer e) {
        this.n = n;
        this.p = p;
        this.e = e;
    }
}*/
public class Main3Activity extends AppCompatActivity {
//code for saving new contact in fb
    private EditText et;
    private TextView tv;
    private CheckBox c;
    private Button sv;
    String num,name;
    ////////
    //private DatabaseReference rootRef,demoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent Extra = getIntent();
        num = Extra.getStringExtra("UserInput");

        tv = (TextView) findViewById(R.id.tv);
        c = (CheckBox) findViewById(R.id.c);
        et = (EditText) findViewById(R.id.et);
        sv = (Button) findViewById(R.id.s);
        tv.setText(num);


        //database reference pointing to root of database
        //rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        //demoRef=rootRef.child("demo");

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //push creates a unique id in database
                name = et.getText().toString();
                //demoRef.push().setValue(name);
                /*Integer t=0;
                if(c.isChecked()){
                    t=1;
                }
                Person person = new Person(name,num,t);


                demoRef = rootRef.child("Person").push();
                demoRef.setValue(person);*/
                //Toast.makeText(Main3Activity.this, "SAVED", Toast.LENGTH_LONG).show();

            }
        });






        ////create a new button with users name



    }
}
