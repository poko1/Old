package com.example.maliha.old;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private ImageButton b1, b3;
    private Button b2, b4;
    String name, number, type="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        b1 = (ImageButton) findViewById(R.id.btnimport); //import contact

        b2 = (Button) findViewById(R.id.btndial); //go to dialpad

        b3 = (ImageButton) findViewById(R.id.btndel); // delete

        b4 = (Button) findViewById(R.id.btnview); //list view

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //import contact
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, 1);

            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Main4Activity.class); //go to dialpad DONE
                startActivity(i);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Main5Activity.class); //go to listview of contacts DONE
                startActivity(i);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Main6Activity.class); //go to del DONE
                startActivity(i);

            }
        });

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {

                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                    // ContactsContract.CommonDataKinds.Phone.TYPE
                            },
                            null, null, null);

                    if (c != null && c.moveToFirst()) {

                        name = c.getString(0);
                        number = c.getString(1);
                        buildAlertMessageEmergency();
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    public void showSelectedNumber(String type, String name, String number) {
        DatabaseHelper db;
        db = new DatabaseHelper(this);
        Contact contact = new Contact(name, number, type);
        boolean insertData = db.addAppoint(contact);


        if (insertData) {
            //Toast.makeText(this, type + ": " + name + ": " + number, Toast.LENGTH_LONG).show();
            toastMessage("Data Successfully Inserted!");
            //Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
            //startActivity(intent);
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void buildAlertMessageEmergency() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);
        alertDialogBuilder
                .setMessage("ADD TO EMERGENCY CONTACT ?")
                .setCancelable(false)//Disable back button action
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        type = "1";
                        showSelectedNumber(type, name, number);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Your negative action
                        type = "0";
                        showSelectedNumber(type, name, number);
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

}
