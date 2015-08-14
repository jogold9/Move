package com.joshbgold.move;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;
    boolean mondayBoolean, tuesdayBoolean, wednesdayBoolean, thursdayBoolean, fridayBoolean, saturdayBoolean, sundayBoolean;
    public boolean alarmToggle = false;  //controls whether alarm should go off

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        //This block of code controls which days reminders are active
        LoadPreferences("Monday", mondayBoolean);
        LoadPreferences("Tuesday", tuesdayBoolean);
        LoadPreferences("Wednesday", wednesdayBoolean);
        LoadPreferences("Thursday", thursdayBoolean);
        LoadPreferences("Friday", fridayBoolean);
        LoadPreferences("Saturday", saturdayBoolean);
        LoadPreferences("Sunday", sundayBoolean);

        //get the current day
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayOfTheWeek = simpleDateFormat.format(date);

        //See if user wants alarm to go off today based on the days he or she chose
        if ((dayOfTheWeek.equals("Monday") || dayOfTheWeek.equals("monday")) && (mondayBoolean == true)){
            alarmToggle = true;
        }
        else if ((dayOfTheWeek.equals("Tuesday") || dayOfTheWeek.equals("tuesday")) && (tuesdayBoolean == true)){
            alarmToggle = true;
        }
        else if ((dayOfTheWeek.equals("Wednesday") || dayOfTheWeek.equals("wednesday")) && (wednesdayBoolean == true)){
            alarmToggle = true;
        }
        else if ((dayOfTheWeek.equals("Thursday") || dayOfTheWeek.equals("thursday")) && (thursdayBoolean == true)){
            alarmToggle = true;
        }
        else if ((dayOfTheWeek.equals("Friday") || dayOfTheWeek.equals("friday")) && (fridayBoolean == true)){
            alarmToggle = true;
        }
        else if ((dayOfTheWeek.equals("Saturday") || dayOfTheWeek.equals("saturday")) && (saturdayBoolean == true)){
            alarmToggle = true;
        }
        else if ((dayOfTheWeek.equals("Sunday") || dayOfTheWeek.equals("sunday")) && (sundayBoolean == true)){
            alarmToggle = true;
        }
        else {
            alarmToggle = false;
        }

        if (alarmToggle == true){
            sendNotification("Move reminder");
        }
    }

    private void sendNotification(String msg) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AlarmActivity.class), 0);

        NotificationCompat.Builder alarmNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Reminder").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);


        alarmNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alarmNotificationBuilder.build());
    }

    //get prefs
    private boolean LoadPreferences(String key, boolean value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getBoolean(key, value);

    }

}
