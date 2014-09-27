package com.example.rony36.simplelogicalarm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateListView();
    }

    private void populateListView(){
        DatabaseHandler db = new DatabaseHandler(this);
        List<Alarm> all_alarms = db.getAllAlarms();

        ListView listView = (ListView) findViewById(R.id.allAlarmsView);
        CustomAdapter adapter = new CustomAdapter(this, all_alarms);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db.close();
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
        if (id == R.id.addAlarm) {
            //Toast.makeText(MainActivity.this, "Keo treat dey na :( ", Toast.LENGTH_SHORT).show();
            DatabaseHandler db = new DatabaseHandler(this);
            db.addAlarm(new Alarm("--:-- --",0,0,0,0,0,0,0,0,0,-1,-1,"/","Alarm "+id));
            db.close();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
