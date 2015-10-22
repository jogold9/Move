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
    private boolean blockWeekendAlarms;
    private boolean blockNonWorkHoursAlarms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText repeatIntervalEditText = (EditText) findViewById(R.id.repeatIntervalInMinutes);
        final Button backButton = (Button) findViewById(R.id.backButton);
        final CheckBox blockWeekendsCheckBox = (CheckBox)findViewById(R.id.blockWeekends);
        final CheckBox blockNonWorkHoursCheckBox = (CheckBox)findViewById(R.id.blockNonWorkDayHours);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

        //load user's previous settings if there were any. May be null.  Then attempt to set the saved values in the layout.
        if(sharedPreferences.contains("volumeKey")) {
            volume = loadPrefs("volumeKey", volume);
            volumeControl.setProgress((int)(volume*100));
        }

        if(sharedPreferences.contains("repeatIntervalKey")) {
            repeatIntervalInMinutes = loadPrefs("repeatIntervalKey", repeatIntervalInMinutes);
            repeatIntervalEditText.setText(repeatIntervalInMinutes + "");
        }

        if (sharedPreferences.contains("noWeekendsKey")) {
            blockWeekendAlarms = loadPrefs("noWeekendsKey", blockWeekendAlarms);
            if (blockWeekendAlarms) {
                blockWeekendsCheckBox.setChecked(true);
            } else {
                blockWeekendsCheckBox.setChecked(false);
            }
        }

        if(sharedPreferences.contains("workHoursOnlyKey")){
            blockNonWorkHoursAlarms = loadPrefs("workHoursOnlyKey", blockNonWorkHoursAlarms);
            if (blockNonWorkHoursAlarms) {
                blockNonWorkHoursCheckBox.setChecked(true);
            }
            else {
                blockNonWorkHoursCheckBox.setChecked(false);
            }
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
                savePrefs("volumeKey", volume);
                Toast.makeText(SettingsActivity.this, "Audio volume is set to: " + progressChanged + " %", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saves user preference for whether to have alarm on weekends
                if (blockWeekendsCheckBox.isChecked()){
                    savePrefs("noWeekendsKey", true);
                    blockWeekendAlarms = loadPrefs("noWeekendsKey", blockWeekendAlarms);
                }
                else{
                    savePrefs("noWeekendsKey", false);
                    blockWeekendAlarms = loadPrefs("noWeekendsKey", blockWeekendAlarms);
                }

                //saves user preference for alarm during work hours / non-work hours
                if (blockNonWorkHoursCheckBox.isChecked()){
                    savePrefs("workHoursOnlyKey", true);
                    blockNonWorkHoursAlarms = loadPrefs("workHoursOnlyKey", blockNonWorkHoursAlarms);
                }
                else {
                    savePrefs("workHoursOnlyKey", false);
                    blockNonWorkHoursAlarms = loadPrefs("workHoursOnlyKey", blockNonWorkHoursAlarms);
                }

                savePrefs("volumeKey", volume);

                Toast.makeText(SettingsActivity.this, "Block reminders on weekends check box is set to: " + blockWeekendAlarms
                        + ". Your alarm will not go off on weekends.", Toast.LENGTH_SHORT).show();
                Toast.makeText(SettingsActivity.this, "Block reminders outside 9 a.m. - 5 p.m. check box is set to: " + blockNonWorkHoursAlarms
                        + "." + " Your alarm will not go off outside 9 a.m. - 5 p.m.", Toast.LENGTH_SHORT).show();

                repeatIntervalAsString = repeatIntervalEditText.getText() + "";

                try {

                    if (repeatIntervalAsString == ""){
                        repeatIntervalInMinutes = 0;
                        savePrefs("repeatIntervalKey", repeatIntervalInMinutes);
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
                            savePrefs("repeatIntervalKey", repeatIntervalInMinutes);
                            finish();
                        }
                    }

                } catch (NumberFormatException exception) {
                    Toast.makeText(SettingsActivity.this, "Please enter a number between 2 and 1440, or leave blank for a one-time alarm.", Toast
                            .LENGTH_LONG).show();
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
