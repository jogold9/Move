package com.joshbgold.move;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private static Context context;
    private final float mediaPlayerVolume = (float)0.3;
    int repeatInterval = 0; //i.e. 1000*60*2 (1000 milliseconds * 60 seconds * 2 repeats alarm every two minutes)
    int repeatIntervalMilliseconds = 0;

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
        final Button cancelAllButton = (Button) findViewById(R.id.cancelAllButton);
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

        mediaPlayer.setVolume(mediaPlayerVolume, mediaPlayerVolume); //sets volume for left & right speakers / headphones
        mediaPlayer.start();

        View.OnClickListener goToSettings = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openSettings();
            }
        };

        //cancel all alarms
        View.OnClickListener cancelAll = new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlarmActivity.getAppContext();
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                if (mediaPlayer != null) try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                } catch (Exception e) {
                    Log.d("Alarm Activity", e.toString());
                }


                Toast.makeText(AlarmActivity.this, "Alarms Canceled", Toast.LENGTH_LONG).show();
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
        cancelAllButton.setOnClickListener(cancelAll);
        exitButton.setOnClickListener(quitApp);
    }

    public void onToggleClicked(View view) {

       if (((ToggleButton) view).isChecked()) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent myIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent, 0);

            repeatInterval = LoadPreferences("repeatInterval", repeatInterval);  //gets number of minutes reminder should repeat

            repeatIntervalMilliseconds = repeatInterval * 1000 * 60;  //converts repeating interval to milliseconds for setRepeating method

            //Set a non-repeating alarm
            if (repeatInterval == 0) {
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(AlarmActivity.this, "Your one time reminder is now set!", Toast.LENGTH_LONG).show();
            }

            //Set a repeating alarm
            else {
                alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), repeatIntervalMilliseconds, pendingIntent);

                if (repeatInterval == 1) {
                    Toast.makeText(AlarmActivity.this, "Reminders are set to repeat every minute.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(AlarmActivity.this, "Reminders are set to repeat every " + repeatInterval + " minutes.", Toast.LENGTH_LONG).show();
                }
            }

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

    //get prefs
    private int LoadPreferences(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int data = sharedPreferences.getInt(key, value);
        return data;
    }

  }