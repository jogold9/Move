package com.joshbgold.move;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by JoshG on 8/9/2015.
 */
public class SetVolumeActivity extends Activity {

    private SeekBar volumeControl = null;
    private float volume = (float) 0.50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_volume);

        final Button backButton = (Button) findViewById(R.id.backButton);

        volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
               /* Toast.makeText(SetVolumeActivity.this, "seek bar progress:" + progressChanged,
                        Toast.LENGTH_SHORT).show();*/

                volume = (float) (((double)(progressChanged))/100);  //allows division w/ decimal results instead of integer results
                savePrefs("volume", volume);
                Toast.makeText(SetVolumeActivity.this, "Audio volume is set to: " + progressChanged + " %", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        backButton.setOnClickListener(goBack);
    }

    //save prefs
    public void savePrefs(String key, float value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

}