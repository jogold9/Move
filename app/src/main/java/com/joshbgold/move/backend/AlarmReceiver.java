package com.joshbgold.move.backend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import java.util.Calendar;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    Context myContext;
    public AlarmReceiver(Context context){
        myContext = context;
    }

    public AlarmReceiver() {

    }

    boolean noWeekends = true;
    boolean workHoursOnly = true;
    boolean variableForTestingOnly = false;

    @Override
    public void onReceive(final Context context, Intent intent) {

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.HOUR_OF_DAY;
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        boolean isWeekend = (today == Calendar.SUNDAY) || (today == Calendar.SATURDAY);
        boolean isOutsideWorkHours = (currentHour < 9) || (currentHour > 17);

        try {  //this value could be null if user has not set it...
            noWeekends = loadPrefs("noWeekends", noWeekends);
            workHoursOnly = loadPrefs("workHoursOnly", workHoursOnly);
            variableForTestingOnly = loadPrefs("testBoolean", variableForTestingOnly);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isWeekend && noWeekends == true) {
            //Alarm is not wanted on the weekend
            try {
                Thread.sleep(1);  //waits for millisecond
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        else if (isOutsideWorkHours  && workHoursOnly == true){
            //Alarm not wanted outside of work hours
            try {
                Thread.sleep(1);  //waits for millisecond
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        else if(variableForTestingOnly == true){
            try {
                Thread.sleep(1);  //waits for millisecond
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        else {
            //Alarm is wanted, and should go off
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