package com.example.maliha.old;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Main6Activity extends AppCompatActivity {

    private ListView contact_list;
    DatabaseHelper helper;
    private static final String TAG = "AppointmentList";
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        getSupportActionBar().setTitle("Contact List");
        helper = new DatabaseHelper(this);
        contact_list = (ListView)findViewById(R.id.appointment_list);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        helper=new DatabaseHelper(this);
        Cursor data = helper.getData();
        final ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){

            listData.add(data.getString(1));
        }
        Collections.sort(listData, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        //create the list adapter and set the adapter
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        contact_list.setAdapter(adapter);
        //set an onItemClickListener to the ListView
        contact_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = helper.getItemID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Main6Activity.this);
                    //alertDialogBuilder.setTitle("Your Title");
                    final int finalItemID = itemID;
                    alertDialogBuilder
                            .setMessage("DELETE SELECTED CONTACT ?")
                            .setCancelable(false)//Disable back button action
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // Your positive action i.e DELETE

                                    helper.deleteName(finalItemID,name);

                                    toastMessage("Contact Deleted!");
                                    Intent intent = new Intent(Main6Activity.this, Main2Activity.class);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // Your negative action
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }



                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }




}

