package com.example.rony36.simplelogicalarm;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rony on 9/11/14.
 */
public class CustomAdapter extends ArrayAdapter<Alarm> {
    List<Alarm> modelItems = null;
    Context context;
    public CustomAdapter(Context context, List<Alarm> resource) {
        super(context,R.layout.per_alarm_activity,resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.modelItems = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.per_alarm_activity, parent, false);

        TextView mTimeView = (TextView) convertView.findViewById(R.id.timeTitle);
        Switch mAlarmOnOff = (Switch) convertView.findViewById(R.id.alarmOnOff);
        CheckBox mRepeatCheck = (CheckBox) convertView.findViewById(R.id.repeatCheckBox);
        ImageView mDrop = (ImageView) convertView.findViewById(R.id.arrowDown);
        ImageView mUp = (ImageView) convertView.findViewById(R.id.arrowUp);
        LinearLayout mDetailsLay = (LinearLayout) convertView.findViewById(R.id.detailsLay);
        ImageButton mRemoveAlarm = (ImageButton) convertView.findViewById(R.id.removeAlarm);

//        TextView name = (TextView) convertView.findViewById(R.id.textView1);
//        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        mTimeView.setText(modelItems.get(position).get_alarm_time());
        if(modelItems.get(position).get_repeat() == 1)
            mRepeatCheck.setChecked(true);
        else
            mRepeatCheck.setChecked(false);
        return convertView;
    }
}
