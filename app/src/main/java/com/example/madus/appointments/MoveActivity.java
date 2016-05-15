package com.example.madus.appointments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MoveActivity extends AppCompatActivity {

    String date;
    String upDatedDate;
    DBManager dbManager;
    Appointment[] appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        setTitle("Move Appointment");
        Intent curIntent = getIntent();
        date = curIntent.getStringExtra("date");

        TextView moveDate = (TextView) findViewById(R.id.moveDate);
        moveDate.setText("Date: "+ date);


        dbManager = new DBManager(this,null,null,1);
        createList();
    }
    Appointment clickedItem;
    public void createList(){
        appointments = dbManager.appointmentsOnDate(date);

        ListView list = (ListView) findViewById(R.id.moveList);
        ListAdapter adapter = new CustomAdapter(this,appointments);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        clickedItem = (Appointment) adapterView.getItemAtPosition(i);
                        final String Item_id = clickedItem.get_id();
                        String Item = clickedItem.get_title();
                        new AlertDialog.Builder(MoveActivity.this)
                                .setTitle("Move Item?")
                                .setMessage("Do you want to Move: \""+ Item +"\" ?")
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        showDialog(0);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_menu_delete)
                                .show();
                    }
                }

        );

    }
    Calendar c = Calendar.getInstance();
    int curryear = c.get(Calendar.YEAR);
    int currmonth = c.get(Calendar.MONTH);
    int currDate = c.get(Calendar.DAY_OF_MONTH);
    ;
    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 0)return new DatePickerDialog(MoveActivity.this,kDatePickerListner,curryear,currmonth,currDate);
        return null;
    }


    protected DatePickerDialog.OnDateSetListener kDatePickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker timePicker, int year, int month,int day) {
            month++;
            String sMonth;
            String sDay;
            if(month<10){sMonth ="0"+String.valueOf(month);} else{sMonth =String.valueOf(month);}
            if(day<10){sDay ="0"+String.valueOf(day);} else{sDay =String.valueOf(day);}

            upDatedDate =sDay+"/"+sMonth+"/"+String.valueOf(year);

            Toast.makeText(getApplicationContext(), upDatedDate,Toast.LENGTH_LONG).show();
            dbManager.updateDate(clickedItem.get_id(),upDatedDate);
            createList();

        }
    };
}
