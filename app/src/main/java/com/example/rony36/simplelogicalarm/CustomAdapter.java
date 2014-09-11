package com.example.rony36.simplelogicalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.per_alarm_activity, parent, false);

        final TextView mTimeView = (TextView) convertView.findViewById(R.id.timeTitle);
        final Switch mAlarmOnOff = (Switch) convertView.findViewById(R.id.alarmOnOff);
        final CheckBox mRepeatCheck = (CheckBox) convertView.findViewById(R.id.repeatCheckBox);
        final ImageView mDrop = (ImageView) convertView.findViewById(R.id.arrowDown);
        final ImageView mUp = (ImageView) convertView.findViewById(R.id.arrowUp);
        final LinearLayout mDetailsLay = (LinearLayout) convertView.findViewById(R.id.detailsLay);
        final ImageButton mRemoveAlarm = (ImageButton) convertView.findViewById(R.id.removeAlarm);

//        TextView name = (TextView) convertView.findViewById(R.id.textView1);
//        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        mTimeView.setText(modelItems.get(position).get_alarm_time());
        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openTimePickerDialog(false);
            }
        });
        if(modelItems.get(position).get_repeat() == 1)
            mRepeatCheck.setChecked(true);
        else
            mRepeatCheck.setChecked(false);

        mDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemoveAlarm.setVisibility(View.VISIBLE);
                mUp.setVisibility(View.VISIBLE);
                mDrop.setVisibility(View.GONE);
                mDetailsLay.setVisibility(View.VISIBLE);
                DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 500, true);
                dropDownAnim.setDuration(500);
                mDetailsLay.startAnimation(dropDownAnim);
            }
        });

        mUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemoveAlarm.setVisibility(View.GONE);
                mUp.setVisibility(View.GONE);
                mDrop.setVisibility(View.VISIBLE);
                DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 500, false);
                dropDownAnim.setDuration(500);
                mDetailsLay.startAnimation(dropDownAnim);
            }
        });

        mRepeatCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mRemoveAlarm.setVisibility(View.VISIBLE);
                    mUp.setVisibility(View.VISIBLE);
                    mDrop.setVisibility(View.GONE);
                    mDetailsLay.setVisibility(View.VISIBLE);
                    DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 500, true);
                    dropDownAnim.setDuration(500);
                    mDetailsLay.startAnimation(dropDownAnim);
                }
            }
        });

        mAlarmOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    status = "ON";
//                    String[] date_str = read_data.split(" ");
//                    String[] coreTime = date_str[0].split(":");
//                    Integer dHour = date_str[1].toCharArray()[0] == 'P' ? Integer.parseInt(coreTime[0])+12 : Integer.parseInt(coreTime[0]);
//                    //Integer milliTime = dHour * 1000 + Integer.parseInt(coreTime[1])* 1000;
//
//                    Calendar calNow = Calendar.getInstance();
//                    Calendar calSet = (Calendar) calNow.clone();
//
//                    calSet.set(Calendar.HOUR_OF_DAY, dHour);
//                    calSet.set(Calendar.MINUTE, Integer.parseInt(coreTime[1]));
//                    calSet.set(Calendar.SECOND, 0);
//                    calSet.set(Calendar.MILLISECOND, 0);
//
//                    if(calSet.compareTo(calNow) <= 0){
//                        //Today Set time passed, count to tomorrow
//                        calSet.add(Calendar.DATE, 1);
//                    }
//
//                    setInstantAlarm(calSet);
//                }else{
//                    status = "OFF";
//                    Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 3, intent, 0);
//                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    am.cancel(pendingIntent);
//                }
            }
        });
        mRemoveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption(position);
                diaBox.show();
            }
        });


        return convertView;
    }
    private AlertDialog AskOption(long id){
        final int alm_id = (int) id;
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getContext())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                        //.setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        db.deleteAlarm(db.getAlarm(alm_id));
                        db.close();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
