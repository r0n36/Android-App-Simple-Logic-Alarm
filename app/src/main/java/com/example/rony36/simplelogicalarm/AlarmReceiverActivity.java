package com.example.rony36.simplelogicalarm;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by RonyLap on 8/30/2014.
 */
public class AlarmReceiverActivity extends Activity {
    private MediaPlayer mMediaPlayer;
    private PowerManager.WakeLock mWakeLock;
    private String[] mList;
//    private String wordList = "Love Fear Wake Hate Good Shine";
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Wake Log");
        mWakeLock.acquire();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,

                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.alarm);

        Button stopAlarm = (Button) findViewById(R.id.btnStopAlarm);
        Button stopMidAlarm = (Button) findViewById(R.id.midBtnStopAlarm);
        Button stopHighAlarm = (Button) findViewById(R.id.hiBtnStopAlarm);
//        String newString;
//        Bundle extras;
//        if (saveInstanceState == null) {
//            extras = getIntent().getExtras();
//            if(extras == null) {
//                newString= null;
//            } else {
//                newString= extras.getString("requestCode");
//            }
//        } else {
//            newString= (String) saveInstanceState.getSerializable("requestCode");
//        }
//        Serializable abc = getIntent().getExtras().getSerializable("requestCode");
        int rowId = getIntent().getIntExtra("requestCode", 1);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        Alarm ringingAlarm = db.getAlarm(rowId);

        final LinearLayout mSimple = (LinearLayout) findViewById(R.id.simpleStop);
        final LinearLayout mMid = (LinearLayout) findViewById(R.id.midStop);
        final LinearLayout mHigh = (LinearLayout) findViewById(R.id.hiStop);

        final EditText mInSol = (EditText) findViewById(R.id.dummyPuzzle);
        final TextView mWoSize = (TextView) findViewById(R.id.woSize);

        final TextView mEquation = (TextView) findViewById(R.id.hiEquation);
        final EditText mEnterRes = (EditText) findViewById(R.id.enterRes);
        final ImageView mImg = (ImageView) findViewById(R.id.imageView);

        if(ringingAlarm.get_off_method() == 1){
           mSimple.setVisibility(View.GONE);
           mMid.setVisibility(View.GONE);
           mHigh.setVisibility(View.VISIBLE);

           mEquation.setText(generateMath(ringingAlarm.get_urgency()));
        }else if (ringingAlarm.get_off_method() == 0){
            mSimple.setVisibility(View.GONE);
            mMid.setVisibility(View.VISIBLE);
            mHigh.setVisibility(View.GONE);

            mWoSize.setText(""+(ringingAlarm.get_urgency()+4)+" Letters");
            // Populating Grid and Puzzle //////////////////////////////////////////////////////////
            int gridSize;
            int gridAdj;
            if (ringingAlarm.get_urgency() == -1){
                gridSize = 5;
                gridAdj = 40;
            }else{
                gridSize = 10;
                gridAdj = 10;
            }
            //TextView mDummy = (TextView) findViewById(R.id.dummyPuzzle);
            String[] list = getPuzzleRaw(ringingAlarm.get_urgency()).split(" ");
            mList = list;
            WordHunt w = new WordHunt(list, gridSize);

            //mDummy.setText(w.toString());

            TableLayout mWordTable = (TableLayout) findViewById(R.id.wordTable);

            for(int i=0; i< gridSize; i++){
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                for(int j=0; j< gridSize; j++){
                    GradientDrawable gd = new GradientDrawable();
                    //gd.setColor(0xFF00FF00);
                    gd.setCornerRadius(5);
                    gd.setStroke(1, 0xFF000000);

                    TextView tv = new TextView(this);
                    tv.setText("" + w.dataF[i][j]);
                    tv.setGravity(Gravity.CENTER);
                    tv.setBackgroundDrawable(gd);
                    int sqr = Math.round(width / gridSize);
                    sqr -= gridAdj;
                    tv.setLayoutParams(new TableRow.LayoutParams(sqr, sqr));
                    tr.addView(tv);
                }
                mWordTable.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }else{
            mImg.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
                public void onSwipeLeft() {
                    // Whatever
                    Toast.makeText(getApplicationContext(), "I'm Left yes no ^_^", Toast.LENGTH_SHORT).show();
                }
            });
        }

        db.close();
        playSound(this, getAlarmUri());

        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
                finish();

            }
        });
        stopMidAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Arrays.asList(mList).contains(mInSol.getText().toString().toUpperCase())){
                    mMediaPlayer.stop();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Oh! Dear! Our algorithm didn't found any Word like this! Wake up and Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        stopHighAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEnterRes.getText().toString().equals(Integer.toString(mResult))){
                    mMediaPlayer.stop();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Oh! Dear! It's not correct. Wake up and Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String mEquation;
    public int mResult;

    public String generateMath(int difficulty){
        int min, max;
        if(difficulty == -1){
            min = 1;
            max = 10;
        }else if(difficulty == 0){
            min = 10;
            max = 99;
        }else{
            min = 100;
            max = 999;
        }

        Random r1 = new Random();
        int i1 = r1.nextInt(max - min) + min;
        Random r2 = new Random();
        int i2 = r2.nextInt(max - min) + min;
        Random r3 = new Random();
        int i3 = r3.nextInt(max - min) + min;

        String op1 = randOperator();
        String op2 = randOperator();

        mEquation = i1 + " " + op1 + " " + i2 + " " + op2 + " " + i3;

        mResult = 0;

        if(op1 == "+"){
            mResult = i1 + i2;
        }else{
            mResult = i1 - i2;
        }

        if(op2 == "+"){
            mResult += i3;
        }else{
            mResult -= i3;
        }
        return mEquation;
    }

    private String randOperator(){
        String[] operators = {"+","-"};
        int idx = new Random().nextInt(operators.length);
        return operators[idx];
    }

    private  void playSound(Context context, Uri alert){
        mMediaPlayer = new MediaPlayer();
        try{
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0){
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        }catch (IOException e){
            Log.i("AlarmReceiver", "No audio files are Found!");
        }
    }

    private Uri getAlarmUri(){
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null){
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null){
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return  alert;
    }

    public String getPuzzleRaw(int urgency){
        String[] raw = new String[3];
        raw[0] = "BAD TRY ASK USE YOU SEE SAY WAY DAY OUR OUT USE BUT WHO GET EYE ONE MAN AND BIG OLD ALL FEW RED FAT BOY FLY ANT ALL ANY CAN SEA DAD INK KEY ODD ZOO YET NAP LOG DEW EGO FUR GIG HAY ICE INN IVY JAR KIT KID LAY LEG SPY TOW HUT HAT ZIP BOX FOX FIX BUM GYM SAX CUP JOY SUM SKY SUN POD PEN PAN";
        raw[1] = "LOVE FEAR WAKE HATE GOOD LIFE TAKE SOME ONLY EVEN MOST LORD PLAY NEXT FEEL WORK FACT HUNT BABY HIGH LOOK EVIL BLUE MIND CLAN LUCK DUCK FREE OVEN FLIP COOL FOLD ACID RAIN BOAT FAIR HEAD SKIN NOSE BIRD CALL DOLL BOIL FOOD BOSS JUNK BUSY MILK HIRE MOON JOKE MAZE DATE DATA BACK BLOW FIND HEAT";
        raw[2] = "HAPPY YOUNG AFTER UNDER ABOVE SMALL CHILD WORLD THING NUMBER LEAVE PERSON WOMAN WOULD THERE BEAUTY CRAZY APPLE AGAIN BRAIN BRING DELAY TOOTH BRAVE BRAVO GROUP EXTRA GREEN PEACE YAHOO WATER CLASH LOBBY LUCKY MOTOR NOISY OCEAN OFFER GREAT PLACE FLOAT PILOT PIVOT PIZZA POUND PRISON BREAK";

        int gSize;
        if (urgency == -1){
            gSize = 15;
        }else{
            gSize = 25;
        }

        String finalStr = "";
        String[] contain = raw[urgency+1].split(" ");
        int conL = contain.length;
        int[] exChecker = new int[gSize];
        for(int i=0; i< gSize; i++){
            int x = randInt(0, conL-1);
            if(Arrays.asList(exChecker).contains(x)){
                x = randInt(0, conL-1);
                exChecker[i] = x;
                finalStr = finalStr+contain[x]+" ";
            }else{
                exChecker[i] = x;
                finalStr = finalStr+contain[x]+" ";
            }
        }
        return finalStr.trim();
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    protected  void onStop(){
        super.onStop();
        mWakeLock.release();
    }
}
