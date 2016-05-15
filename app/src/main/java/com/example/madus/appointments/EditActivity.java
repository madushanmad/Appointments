package com.example.madus.appointments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    TextView scrDate;
    TextView scrTime;
    String time;
    String date;
    String id ;
    int hours;
    int mins;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("View/Edit Appointment");
        dbManager = new DBManager(this,null,null,1);

        Intent curIntent = getIntent();
        date = curIntent.getStringExtra("date");
        id = curIntent.getStringExtra("id");
        updateView();


        scrDate  = (TextView) findViewById(R.id.editViewOnDate);
        scrDate.setText("Date: "+date);
    }

    public void updateView(){
        scrTime = (TextView) findViewById(R.id.editTime);
        EditText scrTitle = (EditText) findViewById(R.id.editTitleDetails);
        EditText scrDetails = (EditText) findViewById(R.id.editDetails);
        Appointment appointment = dbManager.getAppointmentByID(id) ;
        scrTime.setText(appointment.get_time());
        scrTitle.setText(appointment.get_title());
        scrDetails.setText(appointment.get_details());
    }

    public void editTimeButton(View v){
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 0)return new TimePickerDialog(EditActivity.this,kTimePickerListner,hours,mins,true);
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
    public void editSave(View v){
        dbManager.delete(id);

        EditText scrTitle = (EditText) findViewById(R.id.editTitleDetails);
        EditText scrDetails = (EditText) findViewById(R.id.editDetails);

        Appointment appointment = new Appointment(scrTitle.getText().toString(),date,time,scrDetails.getText().toString());
        dbManager.addAppointment(appointment);

        Toast.makeText(getApplicationContext(), "Appointment Edited", Toast.LENGTH_LONG).show();

        Intent setIntent = new Intent(this, ViewActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setIntent.putExtra("date",date);
        startActivity(setIntent);


    }
}
