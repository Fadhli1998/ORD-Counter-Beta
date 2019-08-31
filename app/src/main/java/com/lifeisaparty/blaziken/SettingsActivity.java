package com.lifeisaparty.blaziken;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    Calendar c;
    DatePickerDialog dpd;
    Button savebutton;
    Button setorddateButton;
    TextView orddateTextView;
    String temporddate;
    String ordddate;
    String orddatetowrite;
    String leavequotatowrite;
    String offquotatowrite;
    String leavequota;
    String offquota;
    FileOutputStream fstream;
    EditText leavequotaEditText;
    EditText offquotaEditText;
    double leavequotatowritecheck;
    int leavequotawritelength;
    char leavequotawritechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        savebutton = findViewById(R.id.savebutton);
        setorddateButton = findViewById(R.id.setorddateButton);
        orddateTextView = findViewById(R.id.orddateTextView);

        showOrdDate(); //show ord date on settings launch
        showLeaveQuota(); // show Leave Quota on settings launch
        showOffQuota(); // show Off Quota on settings launch

        /*Button for Calendar Dialog to allow user to set Enlistment date
        setenlistmentdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        tempenlistmentdate = mDay + "/" + (mMonth+1) + "/" + mYear;
                        enlistmentdateTextView.setText(tempenlistmentdate); //set ord date to Text View
                    }
                }, year, month, day);
                dpd.show();

            }
        });*/

        //Button for Calendar Dialog to allow user to set ORD date
        setorddateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        temporddate = mDay + "/" + (mMonth+1) + "/" + mYear;
                        orddateTextView.setText(temporddate); //set ord date to Text View
                    }
                }, year, month, day);
                dpd.show();

            }
        });

        //When user clicks Save, write data to file and go back to main activity
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writeorddatetofile();
                writeleavequotatofile();
                writeoffquotatofile();
                openMainActivity();
            }
        });

    }

    //Functions

    /*show Enlistment Date from file on settings launch function
    public void showEnlistmentDate(){

        try{
            FileInputStream fIn = openFileInput("enlistmentdate.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            enlistmentdate = sb.toString();
            enlistmentdateTextView = findViewById(R.id.enlistmentdateTextView);
            enlistmentdateTextView.setText(enlistmentdate);
            System.out.println("Enlistment Date from file: " + enlistmentdate);
        }
        catch(IOException e)
        {

        }

    }*/

    //show ORD Date from file on settings launch function from file
    public void showOrdDate(){

        try{
            FileInputStream fIn = openFileInput("orddate.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            ordddate = sb.toString();
            orddateTextView = findViewById(R.id.orddateTextView);
            orddateTextView.setText(ordddate);
            System.out.println("ORD Date from file: " + ordddate);
        }
        catch(IOException e)
        {

        }

    }

    //Read Leave Quota from file and display to Leave Quota Edit Text
    public void showLeaveQuota(){

        try{
            FileInputStream fIn = openFileInput("leavequota.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            leavequota = sb.toString();
            leavequotaEditText = findViewById(R.id.leavequotaEditText);
            leavequotaEditText.setText(leavequota);
            System.out.println("Leave Quota from file: " + leavequota);
        }
        catch(IOException e)
        {

        }
    }

    //Read Off Quota from file and display to Leave Quota Edit Text
    public void showOffQuota(){

        try{
            FileInputStream fIn = openFileInput("offquota.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            offquota = sb.toString();
            offquotaEditText = findViewById(R.id.offquotaEditText);
            offquotaEditText.setText(offquota);
            System.out.println("Off Quota from file: " + offquota);
        }
        catch(IOException e)
        {

        }
    }

    //Write Ord date to file
    public void writeorddatetofile(){

        try{
            fstream = openFileOutput("orddate.txt",MODE_PRIVATE);
            orddatetowrite = orddateTextView.getText().toString(); //get ord date from Text View instead of from calendar dialog directly
            fstream.write(orddatetowrite.getBytes());
            fstream.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    //Write Leave Quota to file
    public void writeleavequotatofile(){

        try{
            fstream = openFileOutput("leavequota.txt",MODE_PRIVATE);
            leavequotatowrite = leavequotaEditText.getText().toString(); //get ord date from Text View instead of from calendar dialog directly
            if(leavequotatowrite.equals("")) //if user removes the value, auto default quota value back to 0
            {
                leavequotatowrite = "0";
            }
            fstream.write(leavequotatowrite.getBytes());
            fstream.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    //Write Off Quota to file
    public void writeoffquotatofile(){

        try{
            fstream = openFileOutput("offquota.txt",MODE_PRIVATE);
            offquotatowrite = offquotaEditText.getText().toString(); //get ord date from Text View instead of from calendar dialog directly
            if(offquotatowrite.equals("")) //if user removes the value, auto default quota value back to 0
            {
                offquotatowrite = "0";
            }
            fstream.write(offquotatowrite.getBytes());
            fstream.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    //User click save button and get redirected to main page.  (To ensure that when user backpressed from main activity, it does not return to settings activity)
    public void openMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //ensures the app exits when back button is pressed on Main Activity
        startActivity(intent);
        finish();

    }

}
