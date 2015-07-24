package com.joshbgold.move;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will change to new activity for Reminder(s) at the appropriate time
        Intent myIntent = new Intent();
        myIntent.setClassName("com.joshbgold.move", "com.joshbgold.move.ReminderActivity");
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
        
    }
}
