package com.joshbgold.move;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    public int alarmInterval = 3600000; //equal to 1 hour in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button exitButton = (Button) findViewById(R.id.exitButton);

        //MediaPlayer object is used to play a mp3 file
        final MediaPlayer player;

        //copy raj.mp3 in any drawable folder
        player = MediaPlayer.create(this, R.drawable.om_mani_padme_om);

        //start to play raj.mp3
        player.start();


    /*    AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,System.currentTimeMillis(),alarmInterval, pendingIntent);*/

        //stops the music when exiting the app

        View.OnClickListener quitApp = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                finish();
            }
        };

        exitButton.setOnClickListener(quitApp);
    }
}
