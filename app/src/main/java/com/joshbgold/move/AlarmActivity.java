package com.joshbgold.move;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
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
    private int repeatingInterval = 1000*60*2; //repeat alarm every 2 minutes
    boolean repeat = true;
    private static Context context;

    public AlarmActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Button settingsButton = (Button) findViewById(R.id.settingsButton);
        final Button exitButton = (Button) findViewById(R.id.exitButton);

        AlarmActivity.context = getApplicationContext();  //needed to be able to cancel alarm from another activity

        //MediaPlayer is used to play an mp3 file
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.drawable.om_mani_short);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                mediaplayer.stop();
                mediaplayer.release();
            }
        });

        mediaPlayer.start();

        View.OnClickListener goToSettings = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openSettings();
            }
        };

        View.OnClickListener quitApp = new View.OnClickListener() {  //this block stops music when exiting
            @Override
            public void onClick(View view) {

                if (mediaPlayer != null) try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                } catch (Exception e) {
                    Log.d("Alarm Activity", e.toString());
                }

                finish();
            }
        };

        settingsButton.setOnClickListener(goToSettings);
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

            Toast.makeText(AlarmActivity.this, "Your reminder(s) are set!", Toast.LENGTH_SHORT).show();

        } else {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(AlarmActivity.this, "Your reminder(s) are off.", Toast.LENGTH_SHORT).show();
        }
    }

    public static Context getAppContext(){
        return AlarmActivity.context;
    }

    void openSettings() {
        Intent intent = new Intent(AlarmActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}