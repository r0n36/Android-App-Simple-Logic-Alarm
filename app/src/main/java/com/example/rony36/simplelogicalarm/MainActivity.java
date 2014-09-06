package com.example.rony36.simplelogicalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends Activity {

    private TextView mTimeView, mSat, mSun, mMon, mTue, mWed, mThr, mFri, mAmPmTextView;
    private Switch mAlarmOnOff;
    private CheckBox mRepeatCheck;
    private ImageView mDrop;
    private ImageView mUp;
    private Toast mToast;
    private TimePickerDialog timePickerDialog;
    private LinearLayout mDetailsLay;

    String STORETEXT="storetext.txt";
    String OnOffSTATUS = "onOffStatus.txt";
    String txtToSave, status;
    String read_data = null;
    String read_status = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        List<Alarm> alarms = db.getAllAlarms();
        mTimeView = (TextView) findViewById(R.id.timeTitle);
        mAmPmTextView = (TextView) findViewById(R.id.amPmTextView);
        mAlarmOnOff = (Switch) findViewById(R.id.alarmOnOff);
        mRepeatCheck = (CheckBox) findViewById(R.id.repeatCheckBox);
        mDrop = (ImageView) findViewById(R.id.arrowDown);
        mUp = (ImageView) findViewById(R.id.arrowUp);
        DatabaseHandler db = new DatabaseHandler(this);

        if(db.getAlarmsCount() != 0) {
            Alarm first_alarm = db.getAlarm(1);

            if (first_alarm.get_status() == 1) {
                mAlarmOnOff.setChecked(true);
            } else {
                mAlarmOnOff.setChecked(false);
            }

            mTimeView.setText(first_alarm.get_alarm_time());
            mAmPmTextView.setText("AM");
        }else{
            mAlarmOnOff.setChecked(false);
            mTimeView.setText("--:--");
            mAmPmTextView.setText("--");
        }
        db.close();


        mAlarmOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    status = "ON";
                    String[] date_str = read_data.split(" ");
                    String[] coreTime = date_str[0].split(":");
                    Integer dHour = date_str[1].toCharArray()[0] == 'P' ? Integer.parseInt(coreTime[0])+12 : Integer.parseInt(coreTime[0]);
//                    Integer milliTime = dHour * 1000 + Integer.parseInt(coreTime[1])* 1000;

                    Calendar calNow = Calendar.getInstance();
                    Calendar calSet = (Calendar) calNow.clone();

                    calSet.set(Calendar.HOUR_OF_DAY, dHour);
                    calSet.set(Calendar.MINUTE, Integer.parseInt(coreTime[1]));
                    calSet.set(Calendar.SECOND, 0);
                    calSet.set(Calendar.MILLISECOND, 0);

                    if(calSet.compareTo(calNow) <= 0){
                        //Today Set time passed, count to tomorrow
                        calSet.add(Calendar.DATE, 1);
                    }

                    setInstantAlarm(calSet);
                }else{
                    status = "OFF";
                    Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 3, intent, 0);
                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am.cancel(pendingIntent);
                }
            }
        });

        mDetailsLay = (LinearLayout) findViewById(R.id.detailsLay);
        mRepeatCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mUp.setVisibility(View.VISIBLE);
                    mDrop.setVisibility(View.GONE);
                    mDetailsLay.setVisibility(View.VISIBLE);
                    DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 500, true);
                    dropDownAnim.setDuration(500);
                    mDetailsLay.startAnimation(dropDownAnim);
                }
            }
        });

        mDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                mUp.setVisibility(View.GONE);
                mDrop.setVisibility(View.VISIBLE);
                DropDownAnim dropDownAnim = new DropDownAnim(mDetailsLay, 500, false);
                dropDownAnim.setDuration(500);
                mDetailsLay.startAnimation(dropDownAnim);
            }
        });

        mTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(false);
            }
        });
    }

    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(MainActivity.this, onTimeSetListener,
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

            txtToSave = timeForShow+" "+ am_pm;

            mAlarmOnOff.setChecked(true);

            mTimeView.setText(timeForShow);
            mAmPmTextView.setText(am_pm);

            DatabaseHandler db = new DatabaseHandler(getBaseContext());
            db.addAlarm(new Alarm(txtToSave,1,0,0,0,0,0,0,0,0,-1,-1,"/","Test"));
            db.close();
            setInstantAlarm(calSet);
        }
    };

    private void setInstantAlarm(Calendar timeFromNow){
        try{
            Intent intent = new Intent(MainActivity.this, AlarmReceiverActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(MainActivity.this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, timeFromNow.getTimeInMillis(), pendingIntent);

            if (mToast != null){
                mToast.cancel();
            }


            String time = String.format("%d hours, %d minutes and %d seconds\n",
                    (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / (1000 * 60 * 60),
                    (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / (1000 * 60), (int) (timeFromNow.getTimeInMillis() - System.currentTimeMillis()) / 1000
            );

            mToast = Toast.makeText(getApplicationContext(),
                    "Alarm Set for " +time+" from now",
                    Toast.LENGTH_LONG);
            mToast.show();
        }catch (NumberFormatException e){
            if (mToast != null){
                mToast.cancel();
                mToast = Toast.makeText(MainActivity.this, "Please enter some Number", Toast.LENGTH_LONG);
                mToast.show();
                Log.i("MainActivity", "Number Format exception");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
