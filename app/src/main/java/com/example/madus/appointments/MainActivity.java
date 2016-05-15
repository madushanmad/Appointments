package com.example.madus.appointments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //webapp key: JM2OaZOML9nkbuav67Dd
    String curDate;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendar = (CalendarView) findViewById(R.id.calender);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(calendar.getDate()));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int day) {
                month++;
                String sMonth;
                String sDay;
                if(month<10){sMonth ="0"+String.valueOf(month);} else{sMonth =String.valueOf(month);}
                if(day<10){sDay ="0"+String.valueOf(day);} else{sDay =String.valueOf(day);}

                curDate =sDay+"/"+sMonth+"/"+String.valueOf(year);
                }
        });
        if(curDate==null){curDate =selectedDate;}
        dbManager = new DBManager(this,null,null,1);

    }


    //Buttons
    public void viewEdit(View v){
       /* Appointment[] onDate = dbManager.appointmentsOnDate(curDate);
        String text = " ";
        for(int i=0; i<onDate.length;i++){
            text += onDate[i].get_title();
            Toast toast2 = Toast.makeText(getApplicationContext(), onDate[i].get_id(), Toast.LENGTH_SHORT);
            toast2.show();
        }
        TextView onscreen = (TextView) findViewById(R.id.result);
        onscreen.setText(text);*/
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("date",curDate);
        startActivity(intent);
    }

    public void create(View v){
        Intent intent = new Intent(this, CreateActivity.class);
        intent.putExtra("date",curDate);
        startActivity(intent);
    }

    public void delete(View v){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Options")
                .setPositiveButton("Delete all appointments on date", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Confirm Delete")
                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dbManager.deleteAll(curDate);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_menu_delete)
                                .show();

                    }
                })
                .setNegativeButton("Select appointment to delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectDelete();
                    }
                })
                .setIcon(android.R.drawable.ic_menu_delete)
                .show();
    }

    public void selectDelete(){
        Intent intent = new Intent(this, DeleteActivity.class);
        intent.putExtra("date",curDate);
        startActivity(intent);
    }

    public void search(View v){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void move(View v){
        Intent intent = new Intent(this, MoveActivity.class);
        intent.putExtra("date",curDate);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(this, MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

}
