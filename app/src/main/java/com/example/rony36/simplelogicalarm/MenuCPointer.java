package com.example.rony36.simplelogicalarm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

//pointer class
public class MenuCPointer {
	private Bitmap img;
	private Bitmap imgover;
	private int imgradius;
	private int x = 0;
	private int y = 0;
	private int height = 0;
	private int width = 0;
	private int border = 0;
	private Point homepoint = null;
	private boolean isselected = false;
	
	public MenuCPointer(Context context, int img, int imgover, int border) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        this.border = border;
        this.img = BitmapFactory.decodeResource(context.getResources(), img);
        this.imgover = BitmapFactory.decodeResource(context.getResources(), imgover);
        this.imgradius = this.img.getWidth()/2;
        this.height = this.img.getHeight();
        this.width = this.img.getWidth();
	}
	
	public int get_x() {
		return x;
	}
	
	void set_x(int newValue) {
        x = newValue;
    }

	public int get_y() {
		return y;
	}
	
	void set_y(int newValue) {
        y = newValue;
	}
	
	public Bitmap get_img() {
		if(isselected)
			return imgover;
		else
			return img;
	}
	
	public void set_homeposition(Point point) {
        this.homepoint = point;
	}
	
	public void set_position(int goX, int goY) {
		x = goX;
		y = goY;
	}
	
	public boolean get_isselected() {
		return isselected;
	}
	
	void set_isselected(boolean newValue) {
        isselected = newValue;
	}
	
	public Point get_homepoint() {
		return homepoint;
	}
		
	public int get_imgradius() {
		return imgradius;
	}
	
	public int get_height() {
		return height;
	}
	
	public int get_width() {
		return width;
	}
	
	public int get_border() {
		return border;
	}
}
