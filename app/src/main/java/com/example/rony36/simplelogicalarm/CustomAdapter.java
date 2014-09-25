package com.example.rony36.simplelogicalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        final TextView mAmPm = (TextView) convertView.findViewById(R.id.amPmTextView);
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

        final TextView mNoteTitle = (TextView) convertView.findViewById(R.id.noteTitle);

//        TextView name = (TextView) convertView.findViewById(R.id.textView1);
//        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);

        mNoteTitle.setText(modelItems.get(position).get_note());

        mNoteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText titleInput = new EditText(context);

                // Set the default text to a link of the Queen
                titleInput.setHint("Enter a note or title");

                new AlertDialog.Builder(context)
                        .setTitle("Set Title")
                        .setMessage("Alarm Note")
                        .setView(titleInput)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String title = titleInput.getText().toString();
                                //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
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
        }else{
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

        if(modelItems.get(position).get_repeat() == 1)
            mRepeatCheck.setChecked(true);
        else
            mRepeatCheck.setChecked(false);

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
                    txtSun.setVisibility(View.VISIBLE);
                }else{
                    alm._sun = 0;
                    txtSun.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);
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
                    txtMon.setVisibility(View.VISIBLE);
                }else{
                    alm._mon = 0;
                    txtMon.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);
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
                    txtTue.setVisibility(View.VISIBLE);
                }else{
                    alm._tue = 0;
                    txtTue.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);
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
                    txtThu.setVisibility(View.VISIBLE);
                }else{
                    alm._thu = 0;
                    txtThu.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);
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
                    txtFri.setVisibility(View.VISIBLE);
                }else{
                    alm._fri = 0;
                    txtFri.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);
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
                    txtSat.setVisibility(View.VISIBLE);
                }else{
                    alm._sat = 0;
                    txtSat.setVisibility(View.INVISIBLE);
                }
                db.updateAlarm(alm);
                db.close();
            }
        });


        mDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemoveAlarm.setVisibility(View.VISIBLE);
                mUp.setVisibility(View.VISIBLE);
                mDrop.setVisibility(View.GONE);
                mDetailsLay.setVisibility(View.VISIBLE);
                DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 435, true);
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
                DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 435, false);
                dropDownAnim.setDuration(500);
                mDetailsLay.startAnimation(dropDownAnim);
            }
        });

        mRepeatCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (mDetailsLay.getVisibility() != View.VISIBLE){
                        mRemoveAlarm.setVisibility(View.VISIBLE);
                        mUp.setVisibility(View.VISIBLE);
                        mDrop.setVisibility(View.GONE);
                        mDetailsLay.setVisibility(View.VISIBLE);
                        DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 435, true);
                        dropDownAnim.setDuration(500);
                        mDetailsLay.startAnimation(dropDownAnim);
                    }
                }
            }
        });

        mAlarmOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(context, "Nothing to do in here!", Toast.LENGTH_SHORT).show();
                    // status = "ON";
                    // String[] date_str = read_data.split(" ");
                    // String[] coreTime = date_str[0].split(":");
                    // Integer dHour = date_str[1].toCharArray()[0] == 'P' ? Integer.parseInt(coreTime[0])+12 : Integer.parseInt(coreTime[0]);
                    //Integer milliTime = dHour * 1000 + Integer.parseInt(coreTime[1])* 1000;
                    //
                    // Calendar calNow = Calendar.getInstance();
                    // Calendar calSet = (Calendar) calNow.clone();
                    //
                    // calSet.set(Calendar.HOUR_OF_DAY, dHour);
                    // calSet.set(Calendar.MINUTE, Integer.parseInt(coreTime[1]));
                    // calSet.set(Calendar.SECOND, 0);
                    // calSet.set(Calendar.MILLISECOND, 0);
                    //
                    // if(calSet.compareTo(calNow) <= 0){
                    //     //Today Set time passed, count to tomorrow
                    //     calSet.add(Calendar.DATE, 1);
                    // }
                    //
                    // setInstantAlarm(calSet);
                }else{
                    int mIntent = modelItems.get(position).get_status();
                    Intent intent = new Intent(context, AlarmReceiverActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, mIntent, intent, 0);
                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    am.cancel(pendingIntent);

                    DatabaseHandler db = new DatabaseHandler(getContext());
                    Alarm alm = db.getAlarm(modelItems.get(position)._id);
                    alm._status = 0;
                    db.updateAlarm(alm);
                    db.close();
                }
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
            db.close();

            Alarm alteredAlarm = modelItems.get(listPosition);
            alteredAlarm._alarm_time = txtToSave;
            alteredAlarm._status = 1;
            modelItems.set(listPosition, alteredAlarm);

            notifyDataSetChanged();
            setInstantAlarm(calSet, listPosition);
        }
    };
    private void setInstantAlarm(Calendar timeFromNow, int pos){
        try{
            Intent intent = new Intent(context, AlarmReceiverActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(context, pos, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, timeFromNow.getTimeInMillis(), pendingIntent);


            String time = String.format("%d hours, %d minutes and %d seconds\n",
                    (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / (1000 * 60 * 60),
                    (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / (1000 * 60), (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / 1000
            );

            Toast.makeText(context, "Alarm Set to "+time+" hours from now", Toast.LENGTH_SHORT).show();
        }catch (NumberFormatException e){
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}
