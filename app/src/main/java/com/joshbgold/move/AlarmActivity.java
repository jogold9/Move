package com.joshbgold.move;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

/**
 * Created by JoshG on 7/6/2015.
 * Based on code from http://javapapers.com/android/android-alarm-clock-tutorial/
 */
public class AlarmActivity extends Activity {

    AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private int repeatingInterval = 1000*60*5; //repeat alarm every 5 minutes
    boolean repeat = true;

    public AlarmActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Button exitButton = (Button) findViewById(R.id.exitButton);

        View.OnClickListener quitApp = new View.OnClickListener() {  //this block stops music when exiting
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        exitButton.setOnClickListener(quitApp);
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);

            //use this line if repeat == false
            //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

            //use this line if repeat == true
            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), repeatingInterval, pendingIntent);

            /* We could also use inexact repeating if user wants more variation, but can only use the following long constants
            INTERVAL DAY, INTERVAL_HOUR, INTERVAL_HALF_HOUR, INTERVAL_FIFTEEN_MINUTES*/
            //alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), repeatingInterval, pendingIntent);

            Toast.makeText(AlarmActivity.this, "Your reminder(s) are set!", Toast.LENGTH_LONG).show();

        } else {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(AlarmActivity.this, "Your reminder(s) are off.", Toast.LENGTH_LONG).show();
        }
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

}