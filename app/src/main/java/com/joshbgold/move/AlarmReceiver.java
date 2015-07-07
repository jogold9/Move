package com.joshbgold.move;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by JoshG on 7/6/2015.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        //MediaPlayer object is used to play a mp3 file
        final MediaPlayer player;

        //this will update the UI with message
        AlarmActivity inst = AlarmActivity.instance();
        inst.setAlarmText("Go outside.");

   /*   //this will sound the alarm audio once
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
             alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();*/

        //Call music to be played
        PlayAudio musicPlayer = new PlayAudio();
        musicPlayer.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }


}
