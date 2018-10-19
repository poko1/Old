package com.example.maliha.old;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageButton b1,b2,b3,b4;

    private static final int REQUEST_LOCATION = 1;
    DatabaseHelper helper;
    TextView textView;
    String name, number, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        startService(new Intent(this, UpdateService.class));
        update();

        b1 = (ImageButton) findViewById(R.id.btn1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(i);
            }
        });

    }

    private void update() {

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(Notification.VISIBILITY_SECRET)
                .setPriority(NotificationCompat.PRIORITY_MIN);
                //.setContentTitle("PowerButton Count")
                //.setContentText("Count: " + UpdateService.counter);
        mNotificationManager.notify(UpdateService.mId, mNotifyBuilder.build());
    }

    @Override
    public void onResume(){
        super.onResume();
        update();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        update();
    }

}