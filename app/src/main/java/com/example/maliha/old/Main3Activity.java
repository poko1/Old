package com.example.maliha.old;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {


//code for saving new contact in fb
    private EditText et;
    private TextView tv;
    private CheckBox c;
    private Button sv;
    String n,p,e,num;
    Integer i;
    ////////
    private Contact contact;
    private DatabaseHelper db;
    public static Main3Activity inst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setTitle("Add Contact");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        db = new DatabaseHelper(this);
        Intent Extra = getIntent();
        num = Extra.getStringExtra("UserInput");

        tv = (TextView) findViewById(R.id.tv);
        c = (CheckBox) findViewById(R.id.c);
        et = (EditText) findViewById(R.id.et);
        sv = (Button) findViewById(R.id.s);
        tv.setText(num);


        sv.setOnClickListener(new View.OnClickListener() { //save contacts
            @Override
            public void onClick(View v){

                getAllInputData();

                createContact();

                makeContact(contact);

                 }

        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id= item.getItemId();
        if (id==android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    void getAllInputData() {

        if (c.isChecked()) {
            e="1";
        }
        else{
            e="0";
        }

        n = et.getText().toString();
        p = tv.getText().toString();
    }
    void createContact() {
        contact = new Contact(n, p, e);
    }

    public void makeContact(Contact contact) {
        boolean insertData = db.addAppoint(contact);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
            Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
            startActivity(intent);
        } else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

}