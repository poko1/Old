package com.example.maliha.old;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {
    private ImageButton b, bc;
    private EditText et;
    String s,str;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        et = (EditText) findViewById(R.id.et);

        b = (ImageButton) findViewById(R.id.btnadd); //save contact
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=et.getText().toString().trim();
                if(str.matches("")){ //if number previosly saved then error msg handle kora hoynai
                    //error message
                    Toast.makeText(Main4Activity.this, "No Number Dialed", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(Main4Activity.this, Main3Activity.class);
                    i.putExtra("UserInput", str);
                    startActivity(i);
                }
            }
        });


        bc = (ImageButton) findViewById(R.id.btncall);
        bc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    s = et.getText().toString().trim();
                     makeCall(s);

                } catch (Exception e) {
                    Log.e("DialerAppActivity", "error: " + e.getMessage(),e);//Runtime error will be logged
                }
            }
        });

    }


    public void makeCall(String s)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+s));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            requestForCallPermission();

        } else {
            startActivity(intent);

        }
    }
    public void requestForCallPermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
        {
        }
        else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall(s);
                }
                break;
        }


    }
}
