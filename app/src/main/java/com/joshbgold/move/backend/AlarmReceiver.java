package com.joshbgold.move.backend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    //get the current day
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
    Date date = new Date();
    String dayOfTheWeek = simpleDateFormat.format(date);
    boolean noWeekends = false;

    Context myContext;
    public AlarmReceiver(Context context){
        myContext = context;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        noWeekends = loadPrefs("noWeekends", noWeekends);

        if(dayOfTheWeek == "Saturday" || dayOfTheWeek == "Sunday"  && noWeekends == true) {
            //Alarm is not wanted on the weekend
            try {
                wait(1);  //waits for one-thoousandth of a millisecond
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else {

            Intent myIntent = new Intent();
            myIntent.setClassName("com.joshbgold.move", "com.joshbgold.move.main.ReminderActivity");
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }
    }

    //get prefs
    private boolean loadPrefs(String key,boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(myContext);
        boolean data = sharedPreferences.getBoolean(key, value);
        return data;
    }

}

