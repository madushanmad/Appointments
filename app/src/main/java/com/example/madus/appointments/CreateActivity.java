package com.example.madus.appointments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    TextView scrDate;
    TextView scrTime;
    String time;
    String date;
    int hours;
    int mins;
    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        setTitle("Create Appointment");

        Intent curIntent = getIntent();
        date = curIntent.getStringExtra("date");

        scrDate  = (TextView) findViewById(R.id.onDate);
        scrTime = (TextView) findViewById(R.id.time);
        scrDate.setText("Date: "+date);
        dbManager = new DBManager(this,null,null,1);
    }

    public void timeButton(View v){
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 0)return new TimePickerDialog(CreateActivity.this,kTimePickerListner,hours,mins,true);
        return null;
    }


    protected TimePickerDialog.OnTimeSetListener kTimePickerListner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            hours = i;
            mins = i1;
            time = i+":"+i1;
            scrTime.setText("Time: "+i+":"+i1);
        }
    };

    // Saving Appointments
    public void save(View v){
        EditText scrTitle = (EditText) findViewById(R.id.titleDetails);
        EditText scrDetails = (EditText) findViewById(R.id.details);

        Appointment appointment = new Appointment(scrTitle.getText().toString(),date,time,scrDetails.getText().toString());
        dbManager.addAppointment(appointment);

        Toast.makeText(getApplicationContext(), "Appointment Created", Toast.LENGTH_LONG).show();

        Intent setIntent = new Intent(this, MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);


    }
    static String Ssynonyms = "";
    public void thesaurus(View v){
        EditText scrWord = (EditText) findViewById(R.id.thesaurusText);
        final EditText  scrSynonyms = (EditText) findViewById(R.id.synonyms);
        final String word = scrWord.getText().toString();

        Thread t = new Thread(new Runnable() {
            public void run() {
                ArrayList<String> synonyms = new Thesaurus().SendRequest(word);
                    Ssynonyms = "";
                for(String s:synonyms){
                    Ssynonyms += s+"\n";
                }
                //update(Ssynonyms);
            }
        });
        t.start();

        try {
            t.join();
            update(Ssynonyms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public  void update(String s){
        final EditText  scrSynonyms = (EditText) findViewById(R.id.synonyms);
        scrSynonyms.setText(s);

    }


    public void highlighted(View v){
        EditText et=(EditText)findViewById(R.id.details);

        int startSelection=et.getSelectionStart();
        int endSelection=et.getSelectionEnd();

        String selectedText = et.getText().toString().substring(startSelection, endSelection);



        if(selectedText.matches("[a-zA-Z]+$")){

            replace(selectedText);

        }else{
            Toast.makeText(getApplicationContext(), "Select(Highlight) a word.\n  Text only!", Toast.LENGTH_LONG).show();
        }


    }


    public void replace(final String word){


        final ArrayList<String> synonymlist = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            public void run() {
                ArrayList<String> synonyms = new Thesaurus().SendRequest(word);
                for(String s:synonyms){
                    synonymlist.add(s);
                }
            }
        });
        t.start();

        try {
            t.join();
            final CharSequence[] list = new CharSequence[synonymlist.size()];
            int i =0;
            for(String s:synonymlist){
                list[i++] = s;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Make your selection");
            builder.setItems(list, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    EditText et=(EditText)findViewById(R.id.details);

                    int start = Math.max(et.getSelectionStart(), 0);
                    int end = Math.max(et.getSelectionEnd(), 0);
                    et.getText().replace(Math.min(start, end), Math.max(start, end),list[item].toString(), 0, list[item].toString().length());



                }
            });
            AlertDialog alert = builder.create();
            alert.show();



        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }





}


