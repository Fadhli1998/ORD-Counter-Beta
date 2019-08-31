package com.lifeisaparty.blaziken;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.Calendar;

public class EnlistmentDateActivity extends AppCompatActivity {

    Calendar c;
    DatePickerDialog dpd;
    String enlistmentDate;
    FileOutputStream fstream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlistment_date);

        final Button NextButton = findViewById(R.id.NextButton);
        Button CalendarButton = findViewById(R.id.CalendarButton);
        final TextView EnlistmentDateTextView = findViewById(R.id.EnlistmentDateTextView);
        System.out.println(enlistmentDate);

        //Set CalendarDialog
        CalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(EnlistmentDateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        enlistmentDate = mDay + "/" + (mMonth+1) + "/" + mYear;
                        EnlistmentDateTextView.setText(enlistmentDate); //set ord date to Text View
                    }
                }, year, month, day);
                dpd.show();

            }
        });


        //Save enlistmentdate to file when Next button is clicked and proceed to next activity
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If user does not input enlistment date, display TOAST message and do not proceed to next activity
                if(enlistmentDate==null){
                    Context context = getApplicationContext();
                    CharSequence text = "Enter a Date!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    try{
                        fstream = openFileOutput("enlistmentdate.txt",MODE_PRIVATE);
                        fstream.write(enlistmentDate.getBytes());
                        fstream.close();

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    startActivity(new Intent(EnlistmentDateActivity.this, ServiceDurationActivity.class));
                    finish();
                }
            }
        });

    }
}
