package com.lifeisaparty.blaziken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServiceDurationActivity extends AppCompatActivity {

    RadioButton radioServiceButton;
    FileOutputStream fstream;
    String radioValue;
    int servicedurationint;
    String enlistmentdate;
    String orddate;
    String leavequota = "0";
    String offquota = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_duration);

        final RadioGroup radioGroup = findViewById(R.id.RadioGroup);
        Button NextButton1 = findViewById(R.id.NextButton1);

        NextButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioServiceButton = findViewById(selectedId);
                radioValue = radioServiceButton.getText().toString();

                try
                {
                    FileInputStream fIn = openFileInput("enlistmentdate.txt");
                    InputStreamReader isr = new InputStreamReader(fIn);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    enlistmentdate = sb.toString();
                }
                catch(IOException e)
                {

                }

                if(radioValue.equals("2 YEARS"))
                {
                    servicedurationint = 730;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    try
                    {
                        c.setTime(sdf.parse(enlistmentdate));
                    }
                    catch(ParseException e)
                    {

                    }
                    c.add(Calendar.DATE, servicedurationint); //adding service duration days to enlistment date so as to get ord date
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                    orddate = sdf1.format(c.getTime());
                    try{
                        fstream = openFileOutput("orddate.txt",MODE_PRIVATE);
                        fstream.write(orddate.getBytes());
                        fstream.close();
                        try{ // generate new leave quota file with value 0
                            fstream = openFileOutput("leavequota.txt", MODE_PRIVATE);
                            fstream.write(leavequota.getBytes());
                            fstream.close();
                            try{ //generate new off quota file with value 0
                                fstream = openFileOutput("offquota.txt", MODE_PRIVATE);
                                fstream.write(offquota.getBytes());
                                fstream.close();
                            }
                            catch(Exception e)
                            {

                            }
                        }
                        catch (Exception e)
                        {

                        }

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("Estimated ORD Date activity: " + orddate);
                }
                else if (radioValue.equals("1 YEAR 10 MONTHS"))
                {
                    servicedurationint = 669;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    try
                    {
                        c.setTime(sdf.parse(enlistmentdate));
                    }
                    catch(ParseException e)
                    {

                    }
                    c.add(Calendar.DATE, servicedurationint); //adding service duration days to enlistment date so as to get ord date
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                    orddate = sdf1.format(c.getTime());
                    try{
                        fstream = openFileOutput("orddate.txt",MODE_PRIVATE);
                        fstream.write(orddate.getBytes());
                        fstream.close();
                        try{ // generate new leave quota file with value 0
                            fstream = openFileOutput("leavequota.txt", MODE_PRIVATE);
                            fstream.write(leavequota.getBytes());
                            fstream.close();
                            try{ //generate new off quota file with value 0
                                fstream = openFileOutput("offquota.txt", MODE_PRIVATE);
                                fstream.write(offquota.getBytes());
                                fstream.close();
                            }
                            catch(Exception e)
                            {

                            }
                        }
                        catch(Exception e)
                        {

                        }

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("Estimated ORD Date activity: " + orddate);
                }

                //Once Next button is clicked, save data to serviceduration.txt and proceed to next activity
                try{
                    fstream = openFileOutput("serviceduration.txt",MODE_PRIVATE);
                    fstream.write(radioValue.getBytes());
                    fstream.close();

                }
                catch(Exception e){
                    e.printStackTrace();
                }
                startActivity(new Intent(ServiceDurationActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
