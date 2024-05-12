package com.backyard2015;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

//----------------------------------------------------------------
//
//   dp (device-independent pixel) to bitmap pixel conversion
//
// ----------------------------------------------------------------
public class Ruler {

    public static int dpWidth=100, dpHeight=100;
    public static int pxHeight, pxWidth;   
    public static float density=1.0f;    
    public static Boolean isTablet;
        
    //-------------------------------------------------------------
    public Ruler(Context ctx) {
    	
    	DisplayMetrics metrics= new DisplayMetrics();
           ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                   .getDefaultDisplay().getMetrics(metrics);
           
        int ww, hh;

        density = metrics.density;
        
        pxHeight=metrics.heightPixels;
        pxWidth=metrics.widthPixels;
 
        hh = px2dp(metrics.heightPixels);
        ww = px2dp(metrics.widthPixels);
        
        dpWidth = ww;
        dpHeight = hh;
        //
        // official definition of tablet large screen
        // http://developer.android.com/guide/practices/screens_support.html
        //
        isTablet=( dpWidth >= 640)? true : false;                        
    }
    // ---------------------------------------------------------------- 
    //
    // functions
    //
    // ---------------------------------------------------------------- 
    public int px2dp(final int px) {
        return Math.round((float) px / density);        
    }
    public DP px2dp(final Pixel p) {
        return new DP(Math.round((float) p.px / density));        
    }
    // ----------------------------------------------------------------
    public int dp2px(final int dp) {
        return Math.round((float) dp * density);        
    }
    // ----------------------------------------------------------------
    public Pixel dp2px(final DP d) {
        return new Pixel(Math.round((float) d.dp * density));        
    }    
    // ---------------------------------------------------------------- 
    public static int getDPheight(){
        return dpHeight;
    }
    public static int getDPwidth(){
        return dpWidth;
    }
    //
    //	convert portrait x,y to landscape x,y
    //
    public int toLandX(int x1, int y1){
    	int landX=y1;   	    	
    	return landX;
    }
    public int toLandY(int x1, int y1){
    	int landY;
    
    	if(isTablet){
    		landY=pxWidth-x1-600;
    	}else{
    		landY=pxWidth-x1-300;
    	}
    	return landY;
    }    
    // ----------------------------------------------------------------     
    public Boolean portraitMode(final DisplayMetrics metrics){    	
        return (metrics.heightPixels > metrics.widthPixels) ? true : false;                	                        
    }
    // ----------------------------------------------------------------     
    public static Boolean isVertical(){    	
    	// could be portrait or reverse-portrait
        return (dpWidth <= dpHeight)? true:false;                      
    }      
    
}