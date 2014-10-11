package com.ebipon.apps.simplelogicalarm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//item class
public class MenuCItem {
	private Bitmap img;
	private Bitmap imgover;
	private int imgradius;
	private int x = 0;
	private int y = 0;
	private int height = 0;
	private int width = 0;
	private int border = 0;
	private boolean isover = false;
	private boolean isclick = false;
	private int id = 0;
	
	public MenuCItem(Context context, int img, int imgover, int border, int id) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        this.img = BitmapFactory.decodeResource(context.getResources(), img);
        this.imgover = BitmapFactory.decodeResource(context.getResources(), imgover);
        this.imgradius = this.img.getWidth()/2;
        this.height = this.img.getHeight();
        this.width = this.img.getWidth();
        this.border = border;
        this.id = id;
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
		if(isover)
			return imgover;
		else
			return img;
	}
		
	public void set_position(int goX, int goY) {
		x = goX;
		y = goY;
	}
	
	public boolean get_isover() {
		return isover;
	}
	
	void set_isover(boolean newValue) {
        isover = newValue;
	}
	
	public boolean get_isclick() {
		return isclick;
	}
	
	void set_isclick(boolean newValue) {
        isclick = newValue;
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
	
	public int get_id() {
		return id;
	}
}
