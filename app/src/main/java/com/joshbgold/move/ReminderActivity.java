package com.joshbgold.move;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ReminderActivity extends Activity {

    private TextView DoTheThing;
    private String doThis = "";
    private static PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        DoTheThing = (TextView) findViewById(R.id.doThisThing);
        //alarmTextView = (TextView) findViewById(R.id.alarmText);
        final Button cancelAllButton = (Button) findViewById(R.id.cancelAllButton);
        final Button exitButton = (Button) findViewById(R.id.exitButton);

        //MediaPlayer is used to play an mp3 file
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.drawable.om_mani_short);

        //vibrate the device for 1/2 second if the device is capable
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator.hasVibrator()) {
            vibrator.vibrate(500);
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                mediaplayer.stop();
                mediaplayer.release();
            }
        });

        mediaPlayer.start();

        //Puts random move instruction into text view (i.e. breathe, stretch, go outside, etc).
        ThingsToDo thing = new ThingsToDo();
        doThis = thing.getThingToDo();
        DoTheThing.setText(doThis);

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


                Toast.makeText(ReminderActivity.this, "Alarm Canceled", Toast.LENGTH_LONG).show();
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

        cancelAllButton.setOnClickListener(cancelAll);
        exitButton.setOnClickListener(quitApp);


        //this code block allows for a delay before executing
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //fade in quote to the text edit to replace the previous instructions after 10 seconds
                Quotations quotations = new Quotations();
                doThis = quotations.getQuote();
                DoTheThing.setText(doThis);
            }
        }, 20000);  //equivalent to twenty seconds
    }

}
