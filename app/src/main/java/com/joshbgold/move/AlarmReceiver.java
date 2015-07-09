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

        //MediaPlayer is used to play an mp3 file
  /*      final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.drawable.om_mani_short);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                mediaplayer.stop();
                mediaplayer.release();
            }
        });

        mediaPlayer.start();*/

        //this will send a notification message
/*        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);*/
    }
}
