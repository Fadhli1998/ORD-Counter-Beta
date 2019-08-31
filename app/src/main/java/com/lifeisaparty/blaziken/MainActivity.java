package com.lifeisaparty.blaziken;
//Note all System.out.println are to display each steps as breakpoint

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    String enlistmentdate;
    String orddate;
    String numofdays;
    String leavequota;
    String offquota;
    TextView numofdaysTextView;
    TextView orddateTextView;
    TextView leavequotaTextView;
    TextView offquotaTextView;
    TextView daystoord;
    TextView daystopayday;
    String month;
    String year;
    String paydaydatestring;
    String numofdaystopayday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orddateTextView = findViewById(R.id.orddateTextView);
        leavequotaTextView = findViewById(R.id.leavequotaTextView);
        offquotaTextView = findViewById(R.id.offquotaTextView);
        daystoord = findViewById(R.id.daystoord);

        //Check if enlistmentdate.txt exists in Internal Storage. If enlistmentdate.txt does not exist, the rest does not exist. *bug needs to fix.
        try{
            FileInputStream fIn = openFileInput("enlistmentdate.txt");
            FileInputStream fIn3 = openFileInput("serviceduration.txt"); //also check if serviceduration.txt exists
            System.out.println("Enlistment Date File Found.");

            //If file is found, read enlistmentdate from file and add to variable.
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            enlistmentdate = sb.toString();
            System.out.println("Enlistment Date: " + enlistmentdate);

            //Get ORD Date from file instead of manually calculating on each launch
            try
            {
                FileInputStream fIn1 = openFileInput("orddate.txt");
                InputStreamReader isr1 = new InputStreamReader(fIn1);
                BufferedReader bufferedReader1 = new BufferedReader(isr1);
                StringBuilder sb1 = new StringBuilder();
                String line1;
                while ((line1 = bufferedReader1.readLine()) != null) {
                    sb1.append(line1);
                }
                orddate = sb1.toString();
                orddateTextView.setText(orddate);
                System.out.println("ORD Date from file: " + orddate);
            }
            catch(IOException e)
            {

            }

            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
            try
            {
                Date getcurrentdate = new Date();
                String formattedcurrentdate = sdf2.format(getcurrentdate);
                Date currentdate = sdf2.parse(formattedcurrentdate);
                System.out.println("Todays Date: " + formattedcurrentdate);
                Date orddateend = sdf2.parse(orddate);

                long diff = orddateend.getTime() - currentdate.getTime(); //calculate number of days between enlistment date and ord date
                long diff1 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                numofdays = Long.toString(diff1); //converting long to string so as to be able to use variable as string
                System.out.println("Number of Days till ORD: " + numofdays);

                if(numofdays.equals("0")) //if number of days equals to 0, display ORDLO instead
                {
                    numofdays = "ORDLO!";
                    daystoord.setText("2 YEARS VERY FAST RIGHT?");
                }
                else if(numofdays.equals("1"))
                {
                    daystoord.setText("DAY TO ORD");
                }
                else if(numofdays.charAt(0) == '-'){
                    numofdays = "ORDLO!";
                    daystoord.setText("BRO, YOU ORD ALREADY...");
                }

                numofdaysTextView = findViewById(R.id.numofdaysTextView);
                numofdaysTextView.setText(numofdays);

            }
            catch(ParseException e)
            {

            }

            //Payday
            try{
                Date getcurrentdate = new Date();
                String formattedgetcurrentdate = sdf2.format(getcurrentdate);
                getcurrentdate = sdf2.parse(formattedgetcurrentdate);

                Calendar c = Calendar.getInstance();
                c.setTime(getcurrentdate);

                int currentmonth = Calendar.getInstance().get(Calendar.MONTH);
                int currentyear = Calendar.getInstance().get(Calendar.YEAR);
                int currentday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                System.out.println(currentday);


                if(currentyear == 11)
                {
                    currentyear = currentyear + 1;
                    System.out.println("DID IT WORK: " + currentyear);
                }
                else if(currentday <= 10)
                {
                    currentmonth = currentmonth + 1;
                }
                else{
                    currentmonth = currentmonth + 2;
                }

                month = Integer.toString(currentmonth);
                year = Integer.toString(currentyear);

                paydaydatestring = "10/" + month + "/" + year;
                System.out.println(paydaydatestring);

                Date paydaydate = sdf2.parse(paydaydatestring);

                long diff = paydaydate.getTime() - getcurrentdate.getTime(); //calculate number of days between payday date and ord date
                long diff1 = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                numofdaystopayday = Long.toString(diff1); //converting long to string so as to be able to use variable as string
                System.out.println("Number of Days till Payday: " + numofdaystopayday);

                daystopayday = findViewById(R.id.daystopaydayTextView);
                daystopayday.setText(numofdaystopayday);


            }
            catch(ParseException e)
            {

            }


            try{
                FileInputStream fIn1 = openFileInput("leavequota.txt");
                InputStreamReader isr1 = new InputStreamReader(fIn1);
                BufferedReader bufferedReader1 = new BufferedReader(isr1);
                StringBuilder sb1 = new StringBuilder();
                String line1;
                while ((line1 = bufferedReader1.readLine()) != null) {
                    sb1.append(line1);
                }
                leavequota = sb1.toString();
                leavequotaTextView.setText(leavequota);
                System.out.println("Leave Quota from file: " + leavequota);

                try{
                    FileInputStream fIn2 = openFileInput("offquota.txt");
                    InputStreamReader isr2 = new InputStreamReader(fIn2);
                    BufferedReader bufferedReader2 = new BufferedReader(isr2);
                    StringBuilder sb2 = new StringBuilder();
                    String line2;
                    while ((line2 = bufferedReader2.readLine()) != null) {
                        sb2.append(line2);
                    }
                    offquota = sb2.toString();
                    offquotaTextView.setText(offquota);
                    System.out.println("Off Quota from file: " + offquota);
                }
                catch(IOException e)
                {

                }

            }
            catch(IOException e)
            {

            }


        }
        catch (IOException e){ //If enlistmentdate.txt does not exist, go to enlistmentdateactivity
            System.out.println("File Not Found.");
            startActivity(new Intent(MainActivity.this, EnlistmentDateActivity.class));
            finish();
        }

        //Settings Button to open Settings Menu
        Button settingsbutton = findViewById(R.id.settingsbutton);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

    }

    public void checkdaystopayday(){

    }

}
