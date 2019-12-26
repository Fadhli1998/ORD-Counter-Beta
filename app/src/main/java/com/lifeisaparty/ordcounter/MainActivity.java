package com.lifeisaparty.ordcounter;
//Note all System.out.println are to display each steps as breakpoint

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    Button settingsbutton;
    TextView ord_date_textview;
    TextView leave_quota_textview;
    TextView off_quota_textview;
    TextView payday_textview;
    TextView ord_countdown_textview;
    TextView working_days_textview;
    String orddate;
    int leavequota;
    int offquota;
    int payday;
    String currentdate;
    CircularSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updatecontigency();

        //Initialize all buttons/textviews/edittext here
        settingsbutton = findViewById(R.id.settingsbutton);
        ord_date_textview = findViewById(R.id.ord_date_textview);
        leave_quota_textview = findViewById(R.id.leave_quota_textview);
        off_quota_textview = findViewById(R.id.off_quota_textview);
        payday_textview = findViewById(R.id.payday_textview);
        ord_countdown_textview = findViewById(R.id.ord_countdown_textview);
        working_days_textview = findViewById(R.id.working_days_textview);
        seekBar = findViewById(R.id.seekBar);

        seekBar.isTouchEnabled = false;
        seekBar.setProgress(80);

        Dates date = new Dates();
        currentdate = date.getcurrentdate();

        //Use sharedpreferences to read user data
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        orddate = sharedPreferences.getString("orddate", "");
        leavequota = sharedPreferences.getInt("leavequota", 0);
        offquota = sharedPreferences.getInt("offquota", 0);
        payday = sharedPreferences.getInt("payday", 10);

        ord_date_textview.setText(orddate);
        leave_quota_textview.setText(Integer.toString(leavequota));
        off_quota_textview.setText(Integer.toString(offquota));

        date.orddate = orddate;
        date.payday = payday;
        //Breakpoint to check value
        //System.out.println("Todays date is: " + date.getcurrentdate());
        //System.out.println("Days till ORD: " + date.daystillord());
        //System.out.println("Days till Payday: " + date.daystillpayday());

        ord_countdown_textview.setText(date.daystillord());
        payday_textview.setText(date.daystillpayday());
        working_days_textview.setText(date.workingdays());

        //Open Settings on settings button click
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                finish(); //ensures that the current activity is destroyed
            }
        });
    }

    public void updatecontigency(){ //Massive update from Version 4 to Version 5. Scrapped the use of Internal Files. Using Shared Preferences instead. This function is to facilitate the transition.

        //if orddate.txt exists, user is transition from Version 4
        File file = getBaseContext().getFileStreamPath("orddate.txt");
        System.out.println(file.exists());

        if(file.exists()){
            try{
                FileInputStream fIn1 = getApplicationContext().openFileInput("orddate.txt");
                InputStreamReader isr = new InputStreamReader(fIn1);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                System.out.println(sb);

            } catch (IOException e){

            }
        }

    }

}
