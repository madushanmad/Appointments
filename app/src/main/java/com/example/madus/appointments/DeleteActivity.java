package com.example.madus.appointments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class DeleteActivity extends AppCompatActivity {
    String date;
    DBManager dbManager;
    Appointment[] appointments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        setTitle("Delete Appointment");
        Intent curIntent = getIntent();
        date = curIntent.getStringExtra("date");

        TextView deleteDate = (TextView) findViewById(R.id.deleteDate);
        deleteDate.setText("Date: "+ date);


        dbManager = new DBManager(this,null,null,1);
        createList();
    }

    public void createList(){
        /*
        String[] listItems = new String[appointments.length];
        for(int i=0; i<listItems.length;i++){
            int no = i +1;
            listItems[i] = no +") Time: "+ appointments[i].get_time() + " Title:" + appointments[i].get_title();
            }
        */

        appointments = dbManager.appointmentsOnDate(date);

        ListView list = (ListView) findViewById(R.id.deleteList);
        ListAdapter adapter = new CustomAdapter(this,appointments);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Appointment clickedItem = (Appointment) adapterView.getItemAtPosition(i);
                        final String Item_id = clickedItem.get_id();
                        String Item = clickedItem.get_title();
                        new AlertDialog.Builder(DeleteActivity.this)
                                .setTitle("Confirm Delete")
                                .setMessage("Do you want to delete: \""+ Item +"\" ?")
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dbManager.delete(Item_id);
                                        createList();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_menu_delete)
                                .show();
                    }
                }

        );

    }

}
