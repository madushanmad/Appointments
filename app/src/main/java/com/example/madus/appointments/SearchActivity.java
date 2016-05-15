package com.example.madus.appointments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    Appointment[] appointments;
    DBManager dbManager;
    EditText search;
    boolean render = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search Appointment");
        dbManager = new DBManager(this,null,null,1);
        search = (EditText) findViewById(R.id.searchText);

        appointments = dbManager.allAppointments();
        createList(appointments);

            search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    createList(appointments);
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String text =  s.toString() ;
                    //Toast.makeText(getApplicationContext(), text , Toast.LENGTH_SHORT).show();
                    search(s);


                }
            });
    }

    public void search(CharSequence lookfor){
        ArrayList<Appointment> filterd = new ArrayList<Appointment>();
        for(int i = 0; i<appointments.length;i++){
            if(appointments[i].get_title().contains(lookfor)){
                filterd.add(appointments[i]);
            }else{
                if(appointments[i].get_details().contains(lookfor)){
                    filterd.add(appointments[i]);
                }
            }
        }

        if(!filterd.isEmpty() ){
           Appointment[] toUpdate = new Appointment[filterd.size()];

            createList(filterd.toArray(toUpdate));
           // render = false;
        }

    }





    Appointment clickedItem;
    public void createList(Appointment[] input){

        ListView list = (ListView) findViewById(R.id.searchList);
        ListAdapter adapter = new CustomAdapter(this,input);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        clickedItem = (Appointment) adapterView.getItemAtPosition(i);
                        final String Item_id = clickedItem.get_id();
                        String date = clickedItem.get_date();
                        itemClicked(Item_id,date);
                    }
                }

        );

    }

    public void itemClicked(String id, String date){

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("date",date);
        intent.putExtra("id",id);
        startActivity(intent);

    }


    }



