package com.backyard2015;
import android.content.res.Resources;
import android.graphics.Bitmap.Config;

public class Constant {

	public static final Config BM_CONFIG=
								Config.RGB_565; // no alpha channel

	public static final int ETERNAL_TICK_DELAY=20; //20ms	
	//
	//	preset colors
	//
	public static final int RBRRB_RED=0xffff0000;      
 	public static final int RBRRB_BLACK=0xff000000;	
 	//
 	//	Foot constants
 	//
	public static final String FOOT_LEFT="LEFT", FOOT_RIGHT="RIGHT";
 	//
 	//	Circle drawing constants
 	//
 	public static final int CIRCLE_WIDTH=15; // in pixels NOT dip
	//
	//	status bar height : 24dip, see Official Doc
	//
	//	http://www.google.com/design/spec/patterns/navigation-drawer.html#navigation-drawer-specs
	//
	//-----------------------------------------------------------  			
	public static int statusBarHeight(Resources res) {
	    return (int) (24 * res.getDisplayMetrics().density);
	}
	//-----------------------------------------------------------  			
	public static int getStep(final int length, final int ticks){
		return length /ticks;
	} 	
	//-----------------------------------------------------		
	public static int getCrossGridWidth(final int footww){
		   return footww / 13*3;
	}		
 	//-----------------------------------------------------------  		 	
	//
	//		Class constructor
	//
 	//-----------------------------------------------------------  		 	
	public static final Constant instance = new Constant();	
	public static Constant getInstance() {	return instance;}
	public Constant() {}

	
}



