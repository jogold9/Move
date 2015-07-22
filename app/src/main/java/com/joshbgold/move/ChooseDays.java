package com.joshbgold.move;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ChooseDays extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_days);

        final Button backButton = (Button) findViewById(R.id.backButton);

        //Retrieve the current day of the week
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayOfTheWeek = simpleDateFormat.format(date);

        //Display the current day of the week
        //Toast.makeText(SettingsActivity.this, "Today is " + dayOfTheWeek, Toast.LENGTH_LONG).show();


        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        backButton.setOnClickListener(goBack);
    }



}
