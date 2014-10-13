package com.ebipon.apps.simplelogicalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rony on 9/11/14.
 */
public class CustomAdapter extends ArrayAdapter<Alarm> {
    List<Alarm> modelItems = null;
    Context context;
    LinearLayout mRepeatButts;
    Boolean mGarbTime;
//    CheckBox mRepeatCheck;
    Switch mAlarmOnOff;
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

        final TextView mTimeView        = (TextView) convertView.findViewById(R.id.timeTitle);
        final TextView mAmPm            = (TextView) convertView.findViewById(R.id.amPmTextView);
                       mAlarmOnOff      = (Switch) convertView.findViewById(R.id.alarmOnOff);
        final CheckBox mRepeatCheck     = (CheckBox) convertView.findViewById(R.id.repeatCheckBox);
        final ImageView mDrop           = (ImageView) convertView.findViewById(R.id.arrowDown);
        final ImageView mUp             = (ImageView) convertView.findViewById(R.id.arrowUp);
        final LinearLayout mDetailsLay  = (LinearLayout) convertView.findViewById(R.id.detailsLay);
        final ImageButton mRemoveAlarm  = (ImageButton) convertView.findViewById(R.id.removeAlarm);

        final RadioGroup mUrgencyGrp    = (RadioGroup) convertView.findViewById(R.id.radioWake);
        final RadioGroup mMethodGrp     = (RadioGroup) convertView.findViewById(R.id.radioMethod);

        final RadioButton mWakeLow      = (RadioButton) convertView.findViewById(R.id.wakeLow);
        final RadioButton mWakeMedium   = (RadioButton) convertView.findViewById(R.id.wakeMedium);
        final RadioButton mWakeHigh     = (RadioButton) convertView.findViewById(R.id.wakeHigh);

        final RadioButton mMethodNormal = (RadioButton) convertView.findViewById(R.id.methodNormal);
        final RadioButton mMethodPuzzle = (RadioButton) convertView.findViewById(R.id.methodPuzzle);
        final RadioButton mMethodMath   = (RadioButton) convertView.findViewById(R.id.methodMath);

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

        final TextView mNoteTitle = (TextView) convertView.findViewById(R.id.noteTitle);
        mRepeatButts = (LinearLayout) convertView.findViewById(R.id.repeatButts);


        if(modelItems.get(position).get_urgency() == -1){
            mUrgencyGrp.check(mWakeLow.getId());
        }else if (modelItems.get(position).get_urgency() == 0){
            mUrgencyGrp.check(mWakeMedium.getId());
        }else if (modelItems.get(position).get_urgency() == 1){
            mUrgencyGrp.check(mWakeHigh.getId());
        }

        if(modelItems.get(position).get_off_method() == -1){
            mMethodGrp.check(mMethodNormal.getId());
        }else if (modelItems.get(position).get_off_method() == 0){
            mMethodGrp.check(mMethodPuzzle.getId());
        }else if(modelItems.get(position).get_off_method() == 1){
            mMethodGrp.check(mMethodMath.getId());
        }

        mUrgencyGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Toast.makeText(context, checkedId, Toast.LENGTH_SHORT).show();
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if(mWakeLow.isChecked()) {
                    alm._urgency = -1;
                }else if(mWakeMedium.isChecked()){
                    alm._urgency = 0;
                }else if(mWakeHigh.isChecked()){
                    alm._urgency = 1;
                }
                db.updateAlarm(alm);
                db.close();
            }
        });
        mMethodGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Toast.makeText(context, checkedId, Toast.LENGTH_SHORT).show();
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if(mMethodNormal.isChecked()) {
                    alm._off_method = -1;
                }else if(mMethodPuzzle.isChecked()){
                    alm._off_method = 0;
                }else if(mMethodMath.isChecked()){
                    alm._off_method = 1;
                }
                db.updateAlarm(alm);
                db.close();
            }
        });

        mNoteTitle.setText(modelItems.get(position).get_note());
        mNoteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText titleInput = new EditText(context);

                titleInput.setHint("Enter a note or title");

                new AlertDialog.Builder(context)
                        .setTitle("Set Title")
                        .setMessage("Alarm Note")
                        .setView(titleInput)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String title = titleInput.getText().toString();
                                //Toast.makeText(context, titleInput, Toast.LENGTH_SHORT).show();
                                mNoteTitle.setText(title);
                                DatabaseHandler db = new DatabaseHandler(getContext());
                                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                                alm._note = title;
                                db.updateAlarm(alm);
                                db.close();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
        });

        String[] time = modelItems.get(position).get_alarm_time().split(" ");
        String formatted_time;
        if(time[0].equals("--:--")) {
            formatted_time = "--:--";
            mGarbTime = true;
            mRepeatCheck.setEnabled(false);
            mAlarmOnOff.setEnabled(false);
            for (int i = 0; i < mRepeatButts.getChildCount(); i++) {
                View child = mRepeatButts.getChildAt(i);
                child.setEnabled(false);
            }
        }else{
            mGarbTime = false;
            String[] oTime = time[0].split(":");
            int hour = Integer.parseInt(oTime[0]);
            int min = Integer.parseInt(oTime[1]);
            formatted_time = String.format("%02d", hour) + ":" + String.format("%02d", min);
        }
        mTimeView.setText(formatted_time);
        mAmPm.setText(time[1]);
        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(false, position);
            }
        });

        if(modelItems.get(position).get_status() == 1)
            mAlarmOnOff.setChecked(true);
        else
            mAlarmOnOff.setChecked(false);

        if(modelItems.get(position).get_repeat() == 1){
            mRepeatCheck.setChecked(true);
        }else{
            mRepeatCheck.setChecked(false);

            for (int i = 0; i < mRepeatButts.getChildCount(); i++) {
                View child = mRepeatButts.getChildAt(i);
                child.setEnabled(false);
            }
        }

        if(modelItems.get(position).get_sun() == 1) {
            mSun.setChecked(true);
            txtSun.setVisibility(View.VISIBLE);
        }else {
            mMon.setChecked(false);
            txtSun.setVisibility(View.INVISIBLE);
        }
        if(modelItems.get(position).get_mon() == 1) {
            mMon.setChecked(true);
            txtMon.setVisibility(View.VISIBLE);
        }else{
            mMon.setChecked(false);
            txtMon.setVisibility(View.INVISIBLE);
        }
        if(modelItems.get(position).get_tue() == 1) {
            mTue.setChecked(true);
            txtTue.setVisibility(View.VISIBLE);
        }else{
            mTue.setChecked(false);
            txtTue.setVisibility(View.INVISIBLE);
        }
        if(modelItems.get(position).get_wed() == 1) {
            mWed.setChecked(true);
            txtWed.setVisibility(View.VISIBLE);
        }else{
            mWed.setChecked(false);
            txtWed.setVisibility(View.INVISIBLE);
        }
        if(modelItems.get(position).get_thu() == 1) {
            mThu.setChecked(true);
            txtThu.setVisibility(View.VISIBLE);
        }else {
            mThu.setChecked(false);
            txtThu.setVisibility(View.INVISIBLE);
        }
        if(modelItems.get(position).get_fri() == 1) {
            mFri.setChecked(true);
            txtFri.setVisibility(View.VISIBLE);
        }else{
            mFri.setChecked(false);
            txtFri.setVisibility(View.INVISIBLE);
        }
        if(modelItems.get(position).get_sat() == 1) {
            mSat.setChecked(true);
            txtSat.setVisibility(View.VISIBLE);
        }else{
            mSat.setChecked(false);
            txtSat.setVisibility(View.INVISIBLE);
        }

        mSun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if (isChecked) {
                    alm._sun = 1;
                    alm._repeat = 1;
                    txtSun.setVisibility(View.VISIBLE);
                }else{
                    alm._sun = 0;
                    txtSun.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);

                if(!repeatExist(position)){
                    mRepeatCheck.setChecked(false);
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    alm._repeat = 0;
                    db.updateAlarm(alm);
                }
                updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id, 1);
                db.close();
            }
        });
        mMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if (isChecked) {
                    alm._mon = 1;
                    alm._repeat = 1;
                    txtMon.setVisibility(View.VISIBLE);
                }else{
                    alm._mon = 0;
                    alm._repeat = 1;
                    txtMon.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);

                if(!repeatExist(position)){
                    mRepeatCheck.setChecked(false);
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    alm._repeat = 0;
                    db.updateAlarm(alm);
                }
                updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id, 2);
                db.close();
            }
        });
        mTue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if (isChecked) {
                    alm._tue = 1;
                    alm._repeat = 1;
                    txtTue.setVisibility(View.VISIBLE);
                }else{
                    alm._tue = 0;
                    txtTue.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);

                if(!repeatExist(position)){
                    mRepeatCheck.setChecked(false);
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    alm._repeat = 0;
                    db.updateAlarm(alm);
                }
                updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id, 3);
                db.close();
            }
        });
        mWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if (isChecked) {
                    alm._wed = 1;
                    txtWed.setVisibility(View.VISIBLE);
                }else{
                    alm._wed = 0;
                    txtWed.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);

                if(!repeatExist(position)){
                    mRepeatCheck.setChecked(false);
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    alm._repeat = 0;
                    db.updateAlarm(alm);
                }
                updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id, 4);
                db.close();
            }
        });
        mThu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if (isChecked) {
                    alm._thu = 1;
                    alm._repeat = 1;
                    txtThu.setVisibility(View.VISIBLE);
                }else{
                    alm._thu = 0;
                    txtThu.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);

                if(!repeatExist(position)){
                    mRepeatCheck.setChecked(false);
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    alm._repeat = 0;
                    db.updateAlarm(alm);
                }
                updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id, 5);
                db.close();
            }
        });
        mFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if (isChecked) {
                    alm._fri = 1;
                    alm._repeat = 1;
                    txtFri.setVisibility(View.VISIBLE);
                }else{
                    alm._fri = 0;
                    txtFri.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);

                if(!repeatExist(position)){
                    mRepeatCheck.setChecked(false);
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    alm._repeat = 0;
                    db.updateAlarm(alm);
                }
                updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id, 6);
                db.close();
            }
        });
        mSat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if (isChecked) {
                    alm._sat = 1;
                    alm._repeat = 1;
                    txtSat.setVisibility(View.VISIBLE);
                }else{
                    alm._sat = 0;
                    txtSat.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);

                if(!repeatExist(position)){
                    mRepeatCheck.setChecked(false);
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    alm._repeat = 0;
                    db.updateAlarm(alm);
                }
                updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id, 7);
                db.close();
            }
        });

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        double mHeight = height - (height * 0.6);
        final int targetHeight = (int) Math.round(mHeight);

        mDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemoveAlarm.setVisibility(View.VISIBLE);
                mUp.setVisibility(View.VISIBLE);
                mDrop.setVisibility(View.GONE);
                mDetailsLay.setVisibility(View.VISIBLE);
                DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, targetHeight, true);
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
                DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, targetHeight, false);
                dropDownAnim.setDuration(500);
                mDetailsLay.startAnimation(dropDownAnim);
            }
        });

        mRepeatCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if(isChecked) {
                    alm._repeat = 1;
                    if (mDetailsLay.getVisibility() != View.VISIBLE){
                        mRemoveAlarm.setVisibility(View.VISIBLE);
                        mUp.setVisibility(View.VISIBLE);
                        mDrop.setVisibility(View.GONE);
                        mDetailsLay.setVisibility(View.VISIBLE);
                        DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, targetHeight, true);
                        dropDownAnim.setDuration(500);
                        mDetailsLay.startAnimation(dropDownAnim);
                    }
                    if (!mGarbTime) {
                        mSun.setEnabled(true);
                        mMon.setEnabled(true);
                        mTue.setEnabled(true);
                        mWed.setEnabled(true);
                        mThu.setEnabled(true);
                        mFri.setEnabled(true);
                        mSat.setEnabled(true);
                    }
                    //cancelAlarmHard(position);
                }else {
                    alm._repeat = 0;
                    mSun.setEnabled(false);
                    mMon.setEnabled(false);
                    mTue.setEnabled(false);
                    mWed.setEnabled(false);
                    mThu.setEnabled(false);
                    mFri.setEnabled(false);
                    mSat.setEnabled(false);
                    //cancelAlarmHard(position);
                }
                db.updateAlarm(alm);
                //if (!mGarbTime){
                //    updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id);
                //}
                db.close();
            }
        });

        mAlarmOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cancelAlarmHard(position);
                DatabaseHandler db = new DatabaseHandler(getContext());
                Alarm alm = db.getAlarm(modelItems.get(position)._id);
                if(isChecked){
                    alm._status = 1;
                }else{
                    alm._status = 0;
                }
                db.updateAlarm(alm);
                //updateRepeatingAlarm(alm, getHourFromAlarm(alm), getMinutesFromAlarm(alm), alm._id);
                db.close();
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
    private AlertDialog AskOption(int mId){
        final int alm_id = modelItems.get(mId)._id;
        final int mD = mId;
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getContext())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                        //.setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        cancelAlarmHard(mD);
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
    private void openTimePickerDialog(boolean is24r, long position){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24r);
        listPosition = (int)position;
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    private int listPosition;
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

            DecimalFormat formatter = new DecimalFormat("00");
            String formattedHour = formatter.format(f24h);
            String formattedMin = formatter.format(calSet.get(Calendar.MINUTE));

            String timeForShow = formattedHour+":"+formattedMin;
            String am_pm;
            if(calSet.get(Calendar.AM_PM) == 0) {
                am_pm = "AM";
            }else{
                am_pm = "PM";
            }

            String txtToSave = timeForShow+" "+ am_pm;

            DatabaseHandler db = new DatabaseHandler(getContext());
            Alarm alm = db.getAlarm(modelItems.get(listPosition)._id);
            alm._alarm_time = txtToSave;
            alm._status = 1;
            db.updateAlarm(alm);

            Alarm alteredAlarm = modelItems.get(listPosition);
            alteredAlarm._alarm_time = txtToSave;
            alteredAlarm._status = 1;
            modelItems.set(listPosition, alteredAlarm);

            notifyDataSetChanged();
            mGarbTime = false;
            //mRepeatCheck.setEnabled(true);
            mAlarmOnOff.setEnabled(true);
            int dbId = modelItems.get(listPosition)._id;
            if (alm.get_repeat() != 1 && !repeatExist(listPosition)){
               setInstantAlarm(calSet, dbId);
            }else{
                     if(alm.get_sun() == 1){updateRepeatingAlarm(alm, hourOfDay, minute, dbId, 1);}
                else if(alm.get_mon() == 1){updateRepeatingAlarm(alm, hourOfDay, minute, dbId, 2);}
                else if(alm.get_tue() == 1){updateRepeatingAlarm(alm, hourOfDay, minute, dbId, 3);}
                else if(alm.get_wed() == 1){updateRepeatingAlarm(alm, hourOfDay, minute, dbId, 4);}
                else if(alm.get_thu() == 1){updateRepeatingAlarm(alm, hourOfDay, minute, dbId, 5);}
                else if(alm.get_fri() == 1){updateRepeatingAlarm(alm, hourOfDay, minute, dbId, 6);}
                else if(alm.get_sat() == 1){updateRepeatingAlarm(alm, hourOfDay, minute, dbId, 7);}
            }
            db.close();
        }
    };
    private void updateRepeatingAlarm(Alarm alm, int hourOfDay, int minute, int dbId, int key){
        if (alm.get_repeat() == 1 && alm.get_status() == 1){
            setRepeatAlarm(key, hourOfDay, minute, dbId);
        }
    }

    private void setRepeatAlarm(int weekDay, int hour, int minute, int pos){
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.DAY_OF_WEEK, weekDay);
        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(context, AlarmReceiverActivity.class);
        intent.putExtra("requestCode", pos);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, pos, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(), 1 * 60 * 60 * 1000, pendingIntent);
        Toast.makeText(context, "Next Alarm Set to "+getTimeDifferenceForDisplay(calNow, calSet)+" from now", Toast.LENGTH_SHORT).show();
    }
    private String getTimeDifferenceForDisplay(Calendar from, Calendar to){
        String str = "";
        long dif = (to.getTimeInMillis() - from.getTimeInMillis());

        long seconds    = dif     / 1000;
        long minutes    = seconds / 60;
        long hours      = minutes / 60;
        long days       = hours   / 24;

        if (days != 0)
            str += days + " days ";
        if ((hours % 24) != 0)
            str += (hours % 24) + " hours ";
        if ((minutes % 60) != 0)
            str += (minutes % 60) + " minutes ";

        str += (seconds % 60) + " seconds";
        return str; //(days + " days " + hours % 24 + " hours " + minutes % 60 + " minutes " + seconds % 60 + " seconds");
    }
    private void setInstantAlarm(Calendar timeFromNow, int pos){
        try{
            Intent intent = new Intent(context, AlarmReceiverActivity.class);
            intent.putExtra("requestCode", pos);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context, pos, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, timeFromNow.getTimeInMillis(), pendingIntent);


//            String time =
//                    String.format("%d hours, %d minutes and %d seconds\n",
//                    (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / (1000 * 60 * 60),
//                    (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / (1000 * 60), (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / 1000
//            );

            Toast.makeText(context, "Alarm Set to "+getTimeDifferenceForDisplay(Calendar.getInstance(), timeFromNow)+" from now", Toast.LENGTH_SHORT).show();
        }catch (NumberFormatException e){
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getHourFromAlarm(Alarm alm){
        String time = alm.get_alarm_time().split(" ")[0];
        String amPm = alm.get_alarm_time().split(" ")[1];
        int hourOfDay;
        if (amPm.equals("PM") && Integer.parseInt(time.split(":")[0]) != 12){
            hourOfDay = Integer.parseInt(time.split(":")[0]) + 12;
        }else{
            hourOfDay = Integer.parseInt(time.split(":")[0]);
        }
        return hourOfDay;
    }
    private int getMinutesFromAlarm(Alarm alm){
        String time = alm.get_alarm_time().split(" ")[0];
        return Integer.parseInt(time.split(":")[1]);
    }

    private boolean repeatExist(int position){
        DatabaseHandler db = new DatabaseHandler(getContext());
        Alarm alm = db.getAlarm(modelItems.get(position)._id);
        int tot = alm.get_sun()+alm.get_mon()+alm.get_tue()+alm.get_wed()+alm.get_thu()+alm.get_fri()+alm.get_sat();
        db.close();
        return tot > 0;
    }

    private void cancelAlarmHard(int position){
        int mIntent = modelItems.get(position).get_id();
        Intent intent = new Intent(context, AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, mIntent, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
    }
}
