package com.joshbgold.move.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.joshbgold.move.R;


public class SettingsActivity extends Activity {

    private SeekBar volumeControl = null;
    private float volume = (float) 0.50;
    private String repeatIntervalAsString = "";
    private int repeatIntervalInMinutes = 0;  //Number of minutes that user wants alarm to repeat at (optional)
    private boolean blockWeekendAlarms = true;
    private boolean blockNonWorkAlarms = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText repeatIntervalEditText = (EditText) findViewById(R.id.repeatIntervalInMinutes);
        final Button backButton = (Button) findViewById(R.id.backButton);
        final CheckBox blockWeekends = (CheckBox)findViewById(R.id.blockWeekends);
        final CheckBox blockNonWorkHours = (CheckBox)findViewById(R.id.blockNonWorkDayHours);

        volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

        //load user's previous settings if there were any. May be null.  Then attempt to set the saved values in the layout.
        try {
            volume = loadPrefs("volume", volume);
            volumeControl.setProgress((int)(volume*100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            repeatIntervalInMinutes = loadPrefs("repeatInterval", repeatIntervalInMinutes);
            repeatIntervalEditText.setText(repeatIntervalInMinutes + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            blockWeekendAlarms = loadPrefs("noWeekends", blockWeekendAlarms);
            if (blockWeekendAlarms == true){
                blockWeekends.setChecked(true);
            }
            else{
                    blockWeekends.setChecked(false);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            blockNonWorkAlarms = loadPrefs("workHoursOnly", blockNonWorkAlarms);
            if (blockNonWorkAlarms == true) {
                blockNonWorkHours.setChecked(true);
            }
            else {
                blockNonWorkHours.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

                volume = (float) (((double) (progressChanged)) / 100);  //allows division w/ decimal results instead of integer results
                savePrefs("volume", volume);
                Toast.makeText(SettingsActivity.this, "Audio volume is set to: " + progressChanged + " %", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savePrefs("volume", volume);

                repeatIntervalAsString = repeatIntervalEditText.getText() + "";

                try {

                    if (repeatIntervalAsString == ""){
                        repeatIntervalInMinutes = 0;
                        savePrefs("repeatInterval", repeatIntervalInMinutes);
                        finish();
                    }
                    else {
                        Integer repeatIntervalAsInt = Integer.parseInt(repeatIntervalAsString);
                        if (repeatIntervalAsInt != 0 && repeatIntervalAsInt < 2 || repeatIntervalAsInt > 1440){
                            Toast.makeText(SettingsActivity.this, "Please enter a number between 2 and 1440, or leave blank for a one-time alarm.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            repeatIntervalInMinutes = Integer.valueOf(repeatIntervalAsString);
                            savePrefs("repeatInterval", repeatIntervalInMinutes);
                            finish();
                        }
                    }

                } catch (NumberFormatException exception) {
                    Toast.makeText(SettingsActivity.this, "Please enter a number between 2 and 1440, or leave blank for a one-time alarm.", Toast
                            .LENGTH_LONG).show();
                }

                //saves user preference for whether to have alarm on weekends
                if (blockWeekends.isChecked()){
                    savePrefs("noWeekends", true);
                }
                else{
                    savePrefs("noWeekends", false);
                }

                //saves user preference for alarm during work hours / non-work hours
                if (blockNonWorkHours.isChecked()){
                    savePrefs("workHoursOnly", true);
                }
                else {
                    savePrefs("workHoursOnly", false);
                }
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

    public void savePrefs(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //save prefs
    public void savePrefs(String key, boolean value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //get prefs
    private float loadPrefs(String key,float value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        float data = sharedPreferences.getFloat(key, value);
        return data;
    }

    //get prefs
    private int loadPrefs(String key,int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int data = sharedPreferences.getInt(key, value);
        return data;
    }

    //get prefs
    private boolean loadPrefs(String key,boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean data = sharedPreferences.getBoolean(key, value);
        return data;
    }

}
