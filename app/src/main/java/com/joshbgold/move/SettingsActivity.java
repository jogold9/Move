package com.joshbgold.move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button chooseDaysButton = (Button) findViewById(R.id.chooseDaysButton);
        final Button chooseVolumeButton = (Button) findViewById(R.id.setReminderVolume);
        final Button setRepeatingInterval = (Button) findViewById(R.id.setRepeat);
        final Button backButton = (Button) findViewById(R.id.backButton);

        View.OnClickListener chooseDays = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openChooseDaysActivity();
            }
        };

        View.OnClickListener setVolume = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                launchSetVolumeActivity();
            }
        };

        View.OnClickListener repeatInterval = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                launchRepeatIntervalActivity();
            }
        };

        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        chooseDaysButton.setOnClickListener(chooseDays);
        chooseVolumeButton.setOnClickListener(setVolume);
        setRepeatingInterval.setOnClickListener(repeatInterval);
        backButton.setOnClickListener(goBack);

    }

    void launchSetVolumeActivity(){
        Intent intent = new Intent(SettingsActivity.this, SetVolumeActivity.class);
        startActivity(intent);
    }

    void openChooseDaysActivity(){
        Intent intent = new Intent(SettingsActivity.this, ChooseDays.class);
        startActivity(intent);
    }

    void launchRepeatIntervalActivity(){
        Intent intent = new Intent(SettingsActivity.this, AlarmRepeating.class);
        startActivity(intent);
    }


}
