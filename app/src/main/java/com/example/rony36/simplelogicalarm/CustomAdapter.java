package com.example.rony36.simplelogicalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.ToggleButton;

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
        final ToggleButton mSun = (ToggleButton) convertView.findViewById(R.id.tSun);
        final ToggleButton mMon = (ToggleButton) convertView.findViewById(R.id.tMon);
        final ToggleButton mTue = (ToggleButton) convertView.findViewById(R.id.tTue);
        final ToggleButton mWed = (ToggleButton) convertView.findViewById(R.id.tWed);
        final ToggleButton mThu = (ToggleButton) convertView.findViewById(R.id.tThr);
        final ToggleButton mFri = (ToggleButton) convertView.findViewById(R.id.tFri);
        final ToggleButton mSat = (ToggleButton) convertView.findViewById(R.id.tSat);

        final TextView txtSun = (TextView) convertView.findViewById(R.id.sunTextView);
        final TextView txtMon = (TextView) convertView.findViewById(R.id.monTextView);
        final TextView txtTue = (TextView) convertView.findViewById(R.id.tueTextView);
        final TextView txtWed = (TextView) convertView.findViewById(R.id.wedTextView);
        final TextView txtThu = (TextView) convertView.findViewById(R.id.thrTextView);
        final TextView txtFri = (TextView) convertView.findViewById(R.id.friTextView);
        final TextView txtSat = (TextView) convertView.findViewById(R.id.satTextView);

//        TextView name = (TextView) convertView.findViewById(R.id.textView1);
//        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        mTimeView.setText(modelItems.get(position).get_alarm_time());
        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);
            }
        });

        if(modelItems.get(position).get_repeat() == 1)
            mRepeatCheck.setChecked(true);
        else
            mRepeatCheck.setChecked(false);

        if(modelItems.get(position).get_sun() == 1) {
            mSun.setChecked(true);
            txtSun.setVisibility(View.VISIBLE);
        }else {
            mMon.setChecked(false);
            txtSun.setVisibility(View.GONE);
        }
        if(modelItems.get(position).get_mon() == 1) {
            mMon.setChecked(true);
            txtMon.setVisibility(View.VISIBLE);
        }else{
            mMon.setChecked(false);
            txtMon.setVisibility(View.GONE);
        }
        if(modelItems.get(position).get_tue() == 1) {
            mTue.setChecked(true);
            txtTue.setVisibility(View.VISIBLE);
        }else{
            mTue.setChecked(false);
            txtTue.setVisibility(View.GONE);
        }
        if(modelItems.get(position).get_wed() == 1) {
            mWed.setChecked(true);
            txtWed.setVisibility(View.VISIBLE);
        }else{
            mWed.setChecked(false);
            txtWed.setVisibility(View.GONE);
        }
        if(modelItems.get(position).get_thu() == 1) {
            mThu.setChecked(true);
            txtThu.setVisibility(View.VISIBLE);
        }else {
            mThu.setChecked(false);
            txtThu.setVisibility(View.GONE);
        }
        if(modelItems.get(position).get_fri() == 1) {
            mFri.setChecked(true);
            txtFri.setVisibility(View.VISIBLE);
        }else{
            mFri.setChecked(false);
            txtFri.setVisibility(View.GONE);
        }
        if(modelItems.get(position).get_sat() == 1) {
            mSat.setChecked(true);
            txtSat.setVisibility(View.VISIBLE);
        }else{
            mSat.setChecked(false);
            txtSat.setVisibility(View.GONE);
        }

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
                AlertDialog diaBox = AskOption(modelItems.get(position)._id);
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
    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24r);

        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }
            // calSet return Time Difference. Have to user .getTimeInMillis()
            Integer f24h = calSet.get(Calendar.HOUR_OF_DAY);

            if(calSet.get(Calendar.HOUR_OF_DAY) > 12){
                f24h = calSet.get(Calendar.HOUR_OF_DAY) -12;
            }

            String timeForShow = f24h+":"+calSet.get(Calendar.MINUTE);
            String am_pm;
            if(calSet.get(Calendar.AM_PM) == 0) {
                am_pm = "AM";
            }else{
                am_pm = "PM";
            }

//            txtToSave = timeForShow+" "+ am_pm;

//            mAlarmOnOff.setChecked(true);
//
//            mTimeView.setText(timeForShow);
//            mAmPmTextView.setText(am_pm);

//            DatabaseHandler db = new DatabaseHandler(getBaseContext());
//            db.addAlarm(new Alarm(txtToSave,1,0,0,0,0,0,0,0,0,-1,-1,"/","Test"));
//            db.close();
//            setInstantAlarm(calSet);
        }
    };
}
