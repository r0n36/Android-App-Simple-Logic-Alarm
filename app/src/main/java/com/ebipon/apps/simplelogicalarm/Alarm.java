package com.ebipon.apps.simplelogicalarm;

import java.lang.String;

public class Alarm {

    //private variables
    int _id;
    String _alarm_time;
    int _status;
    int _repeat;
    int _sun;
    int _mon;
    int _tue;
    int _wed;
    int _thu;
    int _fri;
    int _sat;
    int _urgency;
    int _off_method;
    String _ringtone_path;
    String _note;

    // Empty constructor
    public Alarm(){

    }
    // constructor
    public Alarm(int id,
                 String alarm_time,
                 int _status,
                 int _repeat,
                 int _sun,
                 int _mon,
                 int _tue,
                 int _wed,
                 int _thu,
                 int _fri,
                 int _sat,
                 int _urgency,
                 int _off_method,
                 String _ringtone_path,
                 String _note){

        this._id            = id;
        this._alarm_time    = alarm_time;
        this._status        = _status;
        this._repeat        = _repeat;
        this._sun           = _sun;
        this._mon           = _mon;
        this._tue           = _tue;
        this._wed           = _wed;
        this._thu           = _thu;
        this._fri           = _fri;
        this._sat           = _sat;
        this._urgency       = _urgency;
        this._off_method    = _off_method;
        this._ringtone_path = _ringtone_path;
        this._note          = _note;
    }

    public Alarm(String alarm_time,
                 int _status,
                 int _repeat,
                 int _sun,
                 int _mon,
                 int _tue,
                 int _wed,
                 int _thu,
                 int _fri,
                 int _sat,
                 int _urgency,
                 int _off_method,
                 String _ringtone_path,
                 String _note){

        this._alarm_time    = alarm_time;
        this._status        = _status;
        this._repeat        = _repeat;
        this._sun           = _sun;
        this._mon           = _mon;
        this._tue           = _tue;
        this._wed           = _wed;
        this._thu           = _thu;
        this._fri           = _fri;
        this._sat           = _sat;
        this._urgency       = _urgency;
        this._off_method    = _off_method;
        this._ringtone_path = _ringtone_path;
        this._note          = _note;
    }

    // constructor
//    public Alarm(String name, String _phone_number){
//        this._name = name;
//        this._phone_number = _phone_number;
//    }
    // getting ID
    public int get_id(){
        return this._id;
    }

    // setting id
    public void set_id(int id){
        this._id = id;
    }

    // getting name
    public String get_alarm_time(){
        return this._alarm_time;
    }

    // setting name
    public void set_alarm_time(String alarm_time){
        this._alarm_time = alarm_time;
    }

    public int get_status(){
        return this._status;
    }

    public void set_status(int status){
        this._status = status;
    }

    public int get_repeat(){
        return this._repeat;
    }

    public int get_sun(){
        return this._sun;
    }

    public int get_mon(){
        return this._mon;
    }

    public int get_tue(){
        return this._tue;
    }

    public int get_wed(){
        return this._wed;
    }

    public int get_thu(){
        return this._thu;
    }

    public int get_fri(){
        return this._fri;
    }

    public int get_sat(){
        return this._sat;
    }

    public int get_urgency(){
        return this._urgency;
    }

    public int get_off_method(){
        return this._off_method;
    }

    public String get_ringtone_path(){
        return this._ringtone_path;
    }

    public String get_note(){
        return this._note;
    }

    public void set_repeat(int repeat){
        this._repeat = repeat;
    }

    public void set_sun(int sun){
        this._sun = sun;
    }

    public void set_mon(int mon){
        this._mon = mon;
    }

    public void set_tue(int tue){
        this._tue = tue;
    }

    public void set_wed(int wed){
        this._wed = wed;
    }

    public void set_thu(int thu){
        this._thu = thu;
    }

    public void set_fri(int fri){
        this._fri = fri;
    }

    public void set_sat(int sat){
        this._sat = sat;
    }

    public void set_urgency(int urgency){
        this._urgency = urgency;
    }

    public void set_off_method(int off_method){
        this._off_method = off_method;
    }

    public void set_ringtone_path(String ringtone_path){
        this._ringtone_path = ringtone_path;
    }

    public void set_note(String note){
        this._note = note;
    }
}