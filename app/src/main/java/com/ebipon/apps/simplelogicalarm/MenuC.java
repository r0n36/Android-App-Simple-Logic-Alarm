package com.ebipon.apps.simplelogicalarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

//menu class
public class MenuC extends View {
	
	private int center_X = 0;
	private int center_Y = 0;
	
	private Bitmap menuback = null;
	private int menuback_center_X = 0;
	private int menuback_center_Y = 0;
	private int menuback_X = 0;
	private int menuback_Y = 0;
	private int menuback_height = 0;
	private int menuback_width = 0;
	private int menuback_radius = 0;
	private int menuback_border = 0;
		
	private MenuCPointer menupointer = null;
	MenuCItem[] menuitems = new MenuCItem[4];
	
	private int over_engagement = 15; //how much distance to set over item true
	private int click_engagement = 25; //how much distanc to set click item true

    // your path
    Paint mPaint = new Paint();    // your paint
    private Animation anim;

    public MenuC(Context context) {
        super(context);
        setFocusable(true); //necessary for getting the touch events
                
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

//        center_X = ((MainPage)this.getContext()).get_screenWidth()/2;
//        center_Y = ((MainPage)this.getContext()).get_screenHeight()/2;
        center_X = width/2;
        center_Y = height/2;
        
        //menu background
        menuback = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_back);
        menuback_center_X = center_X;
        menuback_center_Y = center_Y;
        menuback_width = menuback.getWidth();
        menuback_height = menuback.getHeight();
        menuback_X = menuback_center_X - menuback_width / 2;
        menuback_Y = menuback_center_Y - menuback_height / 2;
        menuback_radius = menuback_height / 2;
        menuback_border = 35;
        
        //pointer (center)
        menupointer = new MenuCPointer(context, R.drawable.menu_pointer, R.drawable.menu_pointerover, 10);
        menupointer.set_homeposition(new Point(
        		menuback_X + menuback_width / 2 - menupointer.get_width() / 2,
        		menuback_Y + menuback_height / 2 - menupointer.get_height() / 2));
        menupointer.set_position(
        		menupointer.get_homepoint().x,
        		menupointer.get_homepoint().y);
        
        //a item (0)
        menuitems[0] = new MenuCItem(context, R.drawable.menu_item_a, R.drawable.menu_item_aover, 10, 0);
        menuitems[0].set_position(
        		menuback_X - menuitems[0].get_width() / 2 + menuback_width / 2,
        		menuback_Y - menuitems[0].get_height() / 2 + menuback_border);
        
        //b item (3)
        menuitems[1] = new MenuCItem(context, R.drawable.menu_item_b, R.drawable.menu_item_bover, 10, 1);
        menuitems[1].set_position(
        		menuback_X - menuitems[1].get_width() / 2 + menuback_width - menuback_border,
        		menuback_Y - menuitems[1].get_height() / 2 + menuback_height / 2);
        
        //c item (6)
        menuitems[2] = new MenuCItem(context, R.drawable.menu_item_c, R.drawable.menu_item_cover, 10, 2);
        menuitems[2].set_position(
        		menuback_X - menuitems[2].get_width() / 2 + menuback_width / 2,
        		menuback_Y - menuitems[2].get_height() / 2 + menuback_height - menuback_border);
        
        //d item (9)
        menuitems[3] = new MenuCItem(context, R.drawable.menu_item_d, R.drawable.menu_item_dover, 10, 3);
        menuitems[3].set_position(
        		menuback_X - menuitems[3].get_width() / 2 + menuback_border,
        		menuback_Y - menuitems[3].get_height() / 2 + menuback_height / 2);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.WHITE);
        mPaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
    }

    private void createAnimation(Canvas canvas) {
        anim = new RotateAnimation(0, 360, getWidth()/2, getHeight()/2);
        anim.setRepeatMode(Animation.RESTART);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(10000L);
        startAnimation(anim);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int cx = getWidth()/2; // x-coordinate of center of the screen
        int cy = getHeight()/2; // y-coordinate of the center of the screen

        // Starts the animation to rotate the circle.
        if (anim == null) {
            createAnimation(canvas);
        }

        canvas.drawCircle(cx, cy, 150, mPaint); // drawing the circle.
    	//draw thigs on pointer selected
    	if(menupointer.get_isselected())
    	{
    		//draw back menu
    		canvas.drawBitmap(menuback, menuback_X, menuback_Y, null);
    		//draw items
    		for(MenuCItem m : menuitems)
        	{
    			canvas.drawBitmap(m.get_img(), m.get_x(), m.get_y(), null);
        	}
    		//get the item clicked
    		for(MenuCItem m : menuitems)
        	{
    			if(m.get_isclick())
    			{
    	    		//Paint paint = new Paint();
    	    		//paint.setColor(Color.BLACK);
    	    		//paint.setTextSize(20);
    				//canvas.drawText(new Integer(m.get_id()).toString() + " is clicked", 30, 30, paint);
                    //Toast.makeText(getContext(), m.get_id()+" Clicked", Toast.LENGTH_SHORT).show();
                    if(m.get_id() == 3){
                        ((AlarmReceiverActivity)getContext()).mMediaPlayer.stop();
                        ((Activity)(getContext())).finish();
                        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(((AlarmReceiverActivity)getContext()).mReqCode);
                    }
    			}
        	}
    	}
    	//draw pointer
    	canvas.drawBitmap(menupointer.get_img(), menupointer.get_x(), menupointer.get_y(), null);
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	
    	//get current touch position
    	int eventaction = event.getAction(); 
    	int current_x = (int)event.getX(); 
    	int current_y = (int)event.getY();
    	
        switch (eventaction ) { 
        	
        	case MotionEvent.ACTION_DOWN:
        		// check if the finger is on the pointer
	    		int menupointer_x = menupointer.get_x() + menupointer.get_imgradius();
	    		int menupointer_y = menupointer.get_y() + menupointer.get_imgradius();
	    		// distance from the touch pointe to the center of the pointer
	    		double pointer_radius  = Math.sqrt( (double) ( Math.pow(menupointer_x-current_x, 2) + Math.pow(menupointer_y-current_y, 2)) );
	    		//check if the pointer is selected (add some distance)
	    		if (pointer_radius < menupointer.get_imgradius() - 3){
	    			menupointer.set_isselected(true);
	                break;
	    		}
	    		break; 

	        case MotionEvent.ACTION_MOVE:
	        	// move the pointer
	            if (menupointer.get_isselected()) {
	            	//x/y projection to the menu back radius circle
            		int circleset_x = (int) (menuback_center_X + (menuback_radius - menuback_border) * (current_x - menuback_center_X)/ Math.sqrt( Math.pow(current_x - menuback_center_X, 2) + Math.pow(current_y - menuback_center_Y, 2) ));
            		int circleset_y = (int) (menuback_center_Y + (menuback_radius - menuback_border) * (current_y - menuback_center_Y)/ Math.sqrt( Math.pow(current_x - menuback_center_X, 2) + Math.pow(current_y - menuback_center_Y, 2) ));
            		//check max dimensions and project pointer on the circle
            		if(	current_x <= circleset_x && current_y <= circleset_y && current_x <= menuback_center_X && current_y <= menuback_center_Y ||
            			current_x <= circleset_x && current_y >= circleset_y && current_x <= menuback_center_X && current_y >= menuback_center_Y ||
            			current_x >= circleset_x && current_y <= circleset_y && current_x >= menuback_center_X && current_y <= menuback_center_Y ||
            			current_x >= circleset_x && current_y >= circleset_y && current_x >= menuback_center_X && current_y >= menuback_center_Y
            				
            				)
            		{
            			menupointer.set_x(circleset_x - menupointer.get_imgradius());
            			menupointer.set_y(circleset_y - menupointer.get_imgradius());
            		}
            		else
            		{
            			
            			menupointer.set_x(current_x - menupointer.get_imgradius());
    	            	menupointer.set_y(current_y - menupointer.get_imgradius());
            		}
	            	
	            	//check items over
	            	for(MenuCItem m : menuitems)
	            	{
		            	if(
		            		menupointer.get_x() + menupointer.get_border() + over_engagement < m.get_x() + m.get_width() - m.get_border() &&
		            		menupointer.get_x() - menupointer.get_border() + menupointer.get_width() - over_engagement > m.get_x() + m.get_border() &&
		            		menupointer.get_y() + menupointer.get_border() + over_engagement < m.get_y() + m.get_height() - m.get_border() &&
		            		menupointer.get_y() - menupointer.get_border() + menupointer.get_height() - over_engagement > m.get_y() + m.get_border())
		            		m.set_isover(true);
		            	else
		            		m.set_isover(false);
	            	}
	            	//check items click
	            	for(MenuCItem m : menuitems)
	            	{
		            	if(
		            		menupointer.get_x() + menupointer.get_border() + click_engagement < m.get_x() + m.get_width() - m.get_border() &&
		            		menupointer.get_x() - menupointer.get_border() + menupointer.get_width() - click_engagement > m.get_x() + m.get_border() &&
		            		menupointer.get_y() + menupointer.get_border() + click_engagement < m.get_y() + m.get_height() - m.get_border() &&
		            		menupointer.get_y() - menupointer.get_border() + menupointer.get_height() - click_engagement > m.get_y() + m.get_border())
		            		m.set_isclick(true);
		            	else
		            		m.set_isclick(false);
	            	}
	            }
	            break; 
	
	        case MotionEvent.ACTION_UP:
	        	// reset the pointer to home
	        	menupointer.set_isselected(false);
	        	menupointer.set_position(menupointer.get_homepoint().x, menupointer.get_homepoint().y);
	        	break; 
	        } 
        
	        // redraw the canvas
	        invalidate();    
	        return true;
    }
}
