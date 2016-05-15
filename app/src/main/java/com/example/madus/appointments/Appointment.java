package com.example.madus.appointments;

/**
 * Created by madus on 4/19/2016.
 */
public class Appointment {
    private String _id;
    private String _title;
    private String _date;
    private String _time;
    private String _details;

    public Appointment(){}

    public Appointment(String _title, String _date, String _time, String _details) {
        this._title = _title;
        this._date = _date;
        this._time = _time;
        this._details = _details;
    }

    public String get_id() {
        return _id;
    }

    public String get_title() {
        return _title;
    }

    public String get_date() {
        return _date;
    }

    public String get_time() {
        return _time;
    }

    public String get_details() {
        return _details;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public void set_details(String _details) {
        this._details = _details;
    }
}


