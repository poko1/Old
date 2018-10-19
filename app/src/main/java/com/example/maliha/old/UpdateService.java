package com.example.maliha.old;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.Toast;
import java.util.ArrayList;



public class UpdateService extends Service {


    private static long time = 0;
    private BroadcastReceiver mReceiver;
    public static int mId = 34534534;
    public static int counter=0;


    LocationManager locationManager;
    String lattitude,longitude,loc;

    @Override
    public void onCreate() {
        super.onCreate();
        // register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new PowerButtonReceiver();
        registerReceiver(mReceiver, filter);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setVisibility(Notification.VISIBILITY_SECRET)
                        .setPriority(NotificationCompat.PRIORITY_MIN);
                       // .setContentTitle("PowerButton Count")
                       // .setContentText("Count: " +counter);

        startForeground(mId, mBuilder.build());
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        if (!screenOn) {
            time = System.currentTimeMillis();
        } else {
            if(System.currentTimeMillis() - time < 900){
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(new long[] {0, 100}, -1);

                //Storage.getInstance(this).increment();

                counter++;

                Toast.makeText(this, "Power Button Clicked. Swipe up.", Toast.LENGTH_SHORT).show();
                sendMgs();



                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setVisibility(Notification.VISIBILITY_SECRET)
                        .setPriority(NotificationCompat.PRIORITY_MIN);
                        //.setContentTitle("Power Button Counts")
                        //.setContentText("Count: " +counter);
                mNotificationManager.notify(mId, mNotifyBuilder.build());
            }
            time = 0;
        }
        return super.onStartCommand(intent,flags,startId);
    }

    private void sendMgs() {


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        String separator = "; ";
        if(android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")){
            separator = ", ";
        }
        /*try {

            if(loc.equals(""))
            {

            }
            else {*/


                String strnum2="";
                DatabaseHelper helper=new DatabaseHelper(this);
                Cursor data = helper.geteData();
                final ArrayList<String> listData = new ArrayList<>();
                while(data.moveToNext()){
                    listData.add(data.getString(2));
                }
                for (int i = 0; i < listData.size(); i++) {
                    String temp = listData.get(i);
                    if (i == 0) strnum2 = temp;
                    else strnum2 += separator + temp;
                }

                //Uri smsToUri = Uri.parse("smsto:" + strnum2);
                //Intent intent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
                String message = loc;
                message += "\nPLEASE DO NOT IGNORE";
                //intent.putExtra("sms_body", message);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);

                sendSMS(strnum2,message);


           /* }
        } /*catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*/


    }

    public void sendSMS(String phn, String msg) {

        try {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(phn, null, msg, null, null);

            //progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "WARNING SMS SENT", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            // progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Unable To Send SMS Warning. Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                loc="";
                loc += "HELP!! MY LOCATION IS:\n";
                loc += "http://www.google.com/maps/place/"+lattitude+","+longitude;
            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                loc="";
                loc += "HELP!! MY LOCATION IS:\n";
                loc += "http://www.google.com/maps/place/"+lattitude+","+longitude;

            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                loc="";
                loc += "HELP ME!! MY LOCATION IS:\n";
                loc += "http://www.google.com/maps/place/"+lattitude+","+longitude;
                String url = "https://iradivi.com/test/?latitude=" + lattitude +"&longitude=" + longitude;


            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onDestroy(){
        unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
