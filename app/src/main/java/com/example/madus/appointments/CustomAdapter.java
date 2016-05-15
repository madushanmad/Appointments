package com.example.madus.appointments;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by madus on 4/19/2016.
 */
class CustomAdapter extends ArrayAdapter<Appointment> {
    int itemno = 1;
    Appointment[] incoming;


    public CustomAdapter(Context context, Appointment[] appointments) {
        super(context,R.layout.custom_row, appointments);
        incoming = appointments;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row,parent,false);

        Appointment appointment = getItem(position);
        TextView scrItemNo = (TextView) customView.findViewById(R.id.listItemNo);
        TextView scrItemName = (TextView) customView.findViewById(R.id.listItemName);
        TextView scrItemDetails = (TextView) customView.findViewById(R.id.listItemDetails);
        TextView scrItemDetails2 = (TextView) customView.findViewById(R.id.listItemDetails2);


        scrItemNo.setText(Integer.toString(itemno)+". ");
        if(itemno<incoming.length){itemno++;}else {itemno = 1 ;}
        scrItemName.setText(appointment.get_title());
        scrItemDetails.setText("Time: "+appointment.get_time());
        scrItemDetails2.setText("Date: "+appointment.get_date());
        return customView;

    }
}
