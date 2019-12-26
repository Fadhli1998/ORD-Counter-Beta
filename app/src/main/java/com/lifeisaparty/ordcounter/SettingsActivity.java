package com.lifeisaparty.ordcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
import java.util.Calendar;


public class SettingsActivity extends AppCompatActivity {

    Button savebutton;
    Button setorddatebutton;
    TextView settings_ord_date_textview;
    EditText leave_quota_edittext;
    EditText off_quota_edittext;
    String orddate;
    int leavequota;
    int offquota;
    int payday;
    Calendar c;
    DatePickerDialog dpd;
    RadioButton tenth_radiobutton;
    RadioButton twelve_radiobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Initialize all buttons/textviews/edittext here
        settings_ord_date_textview = findViewById(R.id.settings_ord_textview);
        leave_quota_edittext = findViewById(R.id.leave_quota_edittext);
        off_quota_edittext = findViewById(R.id.off_quota_edittext);
        savebutton = findViewById(R.id.savebutton);
        setorddatebutton = findViewById(R.id.set_ord_date_button);
        tenth_radiobutton = findViewById(R.id.tenth_radiobutton);
        twelve_radiobutton = findViewById(R.id.twelve_radiobutton);

        //Use sharedpreferences to save user data
        final SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        settings_ord_date_textview.setText(sharedPreferences.getString("orddate", ""));
        leave_quota_edittext.setText(Integer.toString(sharedPreferences.getInt("leavequota", 0)));
        off_quota_edittext.setText(Integer.toString(sharedPreferences.getInt("offquota", 0)));
        payday = sharedPreferences.getInt("payday", 0);

        //Check radio button according to user saved payday on SharedPreferences
        if(payday == 10){
            tenth_radiobutton.setChecked(true);
        }
        else if(payday == 12) {
            twelve_radiobutton.setChecked(true);
        }

        //Open Calendar Dialog when user presses the Calendar Edit button and show selected date on ord_date_edittext
        setorddatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        settings_ord_date_textview.setText(mDay + "/" + (mMonth+1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        //Save settings on save button click & proceed to main activity
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orddate = settings_ord_date_textview.getText().toString();
                leavequota = Integer.parseInt(leave_quota_edittext.getText().toString());
                offquota = Integer.parseInt(off_quota_edittext.getText().toString());

                //save payday according to user input radio
                if(tenth_radiobutton.isChecked()){
                    payday = 10;
                }
                else if(twelve_radiobutton.isChecked()){
                    payday = 12;
                }


                editor.putString("orddate", orddate);
                editor.putInt("leavequota", leavequota);
                editor.putInt("offquota", offquota);
                editor.putInt("payday", payday);

                editor.commit();

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); //ensures that the current activity is destroyed

            }
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
        finish(); //ensures that the current activity is destroyed
    }

}
