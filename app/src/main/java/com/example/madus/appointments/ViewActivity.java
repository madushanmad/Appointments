package com.example.madus.appointments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    String date;
    DBManager dbManager;
    Appointment[] appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        setTitle("View Appointment");
        Intent curIntent = getIntent();
        date = curIntent.getStringExtra("date");

        TextView moveDate = (TextView) findViewById(R.id.ViewDate);
        moveDate.setText("Date: "+ date);


        dbManager = new DBManager(this,null,null,1);
        createList();
    }

    Appointment clickedItem;
    public void createList(){
        appointments = dbManager.appointmentsOnDate(date);

        ListView list = (ListView) findViewById(R.id.ViewList);
        ListAdapter adapter = new CustomAdapter(this,appointments);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        clickedItem = (Appointment) adapterView.getItemAtPosition(i);
                        final String Item_id = clickedItem.get_id();
                        itemClicked(Item_id);
                    }
                }

        );

    }

    public void itemClicked(String id){

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("date",date);
        intent.putExtra("id",id);
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
