package com.backyard2015;

import android.graphics.Bitmap;
import android.graphics.Canvas;
//import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
//
//		Speed Optimization Tricks:
//
// 		.drawBitmap() without Rect specified causes lagging in runtime
// 		i.e. prefer drawBitmap with Rect spec for stable speed
//
//
public class Foot {
 
   private int 		ySect1, ySect2, ySect3, ySect4;
   private int 		footWidth, footHeight;
   public  Point 	pos;
   private final 	Paint paint=new Paint();
   private Paint 	pred=new Paint();
   private String 	which; // value: Constant.FOOT_LEFT or RIGHT
	   
   private Flicker 	flickSect1, flickSect1c, flickSect2, 
   					flickSect3, flickSect4, flickSect4bar;	
   //	   
   //	Section 1
   //
   private Bitmap bmSect1;
   private Canvas cvsSect1;   
   private Bitmap bmGridSect1;
   private Canvas cvsGridSect1;
   private CrossDrawer crossSect1;  
   //	   
   //	Section 2
   //         
   private Bitmap 			bmSect2;
   private Canvas 			cvsSect2;   
   private Bitmap 			bmSect2red; 
   private Canvas 			cvsSect2red; 

   private Bitmap 			bmCrossSect2;
   private Canvas 			cvsCrossSect2;
   private SwipeVertical 	swipeVsect2;		// 4 bmCrossSect2 positioning   
   private CrossDrawer 		crossSect2; 		// to draw Cross graphics	    
   //	   
   //	Section 3
   //    
   private Bitmap bmSect3, bmSect3a;
   private Canvas cvsSect3, cvsSect3a; 
   //	   
   //	Section 4
   //         
   private Bitmap 			bmSect4; // large main bitmap covering all stuff
   private Canvas  			cvsSect4;   
   
   private Bitmap  			bmSect4a; 
   private Canvas  			cvsSect4a;
   private int				xSect4a;
   
   private Bitmap  			bmSect4b; 
   private Canvas  			cvsSect4b;   
   private int				xSect4b;
   
   private Bitmap 			bmSect4cross;
   private Canvas 			cvsSect4cross;
   private int 				xSect4cross;
   private SwipeVertical 	swipeVsect4;   
   private SwipeHorizon 	swipeHorizonSect4;
      
   private Bitmap 			bmSect4bar;
   private Canvas 			cvsSect4bar;   
   private int 				xSect4bar;
   private Sect4Mapping 	mapInfo;   
   private CrossDrawer 		crossSect4;
   
   //
   //	global
   //
   private CircleDrawer cir;   
   private int gridWidth;
   //---------------------------------------------------------------------------------------------------
   public Foot(Ruler rulerIn,  final String whichIn, final int statusBarH){
	   //
	   //	init Global Values
	   //
	   which=whichIn; // left or right	   	   
	   footWidth=rulerIn.pxWidth/2;
	   footHeight=rulerIn.pxHeight;
	   gridWidth=Constant.getCrossGridWidth(footWidth);	   
	   crossSect1=new CrossDrawer(footWidth);	   
	   //
	   //	determine starting position of all sections
	   //
	   ySect1=statusBarH; 			// is 0 if no status bar	   
	   ySect2=ySect1+gridWidth; 	// old default value is ySect1+50 
	   ySect3= ySect2 + gridWidth*7; //+(footWidth*2);	
	   ySect4=ySect3+gridWidth*2;
	   //
	   // 	position of a foot bitmap on screen (i.e. either left or right)
	   //	 
	   if(which.equals(Constant.FOOT_LEFT)){
		   pos=new Point(0,0);
	   }else{
		   pos=new Point(footWidth,0);
	   }  
	   //	   
	   //	init section 1
	   //
	   initFootSect1();	  	   		   
	   initFootSect2(rulerIn);
	   initFootSect3(rulerIn);
	   initSect4wall(rulerIn);   	   
	   initFootSect4(rulerIn);
	   //
	   //
	   //	init flicker timer (not thread, not timerdelay, just plain counter)
	   //
	   flickSect1=new Flicker(10);	   
	   flickSect1c=new Flicker(5);	 	   
	   flickSect2=new Flicker(2);
	   flickSect3=new Flicker(2*2);
	   flickSect4=new Flicker(10);
	   flickSect4bar=new Flicker(4);
   }
   //---------------------------------------------------------------------------------------------------      
   private void initFootSect1(){
	   
	   bmSect1=Bitmap.createBitmap(footWidth, 
			   ySect2-ySect1, 
			   Constant.BM_CONFIG);
	   cvsSect1=new Canvas(bmSect1);
	   
	   bmGridSect1=Bitmap.createBitmap(footWidth+gridWidth,
	   							// bmSect1.getWidth(), 
	   							bmSect1.getHeight(), Constant.BM_CONFIG);
	   cvsGridSect1=new Canvas(bmGridSect1);
	   cvsGridSect1.drawColor(Constant.RBRRB_RED);
	   
	   for(int xx1=0; xx1 < bmGridSect1.getWidth(); xx1+=gridWidth*2){
		   //cvsGridSect1.drawBitmap(crossSect1.drawGrid(xx1,0), xx1,0, paint);
		   cvsGridSect1.drawBitmap(
				   crossSect1.drawGrid(0,0), 
				   new Rect(0,0,gridWidth,gridWidth), // a row of grid 
				   new Rect(xx1,0,xx1+gridWidth,gridWidth), 
				   paint);	   
	   }	   
  	   	   	   
   }
   //---------------------------------------------------------------------------------------------------
   private void initFootSect2(final Ruler rulerIn){
	   
	   bmSect2=Bitmap.createBitmap(footWidth, ySect3-ySect2, Constant.BM_CONFIG);
	   cvsSect2=new Canvas(bmSect2);
	   cvsSect2.drawColor(Constant.RBRRB_BLACK);	   

	   
	   pred.setColor(Constant.RBRRB_RED);   
	   final int widthRed=getSect2redWidth(footWidth);
	   
	   bmSect2red=Bitmap.createBitmap(widthRed, ySect3-ySect2, Constant.BM_CONFIG);
	   cvsSect2red=new Canvas(bmSect2red);
	   cvsSect2red.drawColor(Constant.RBRRB_RED);
	   
	   //
	   //		Section 2 cross
	   //
	   swipeVsect2=new SwipeVertical(rulerIn, 0-gridWidth,bmSect2.getHeight()- gridWidth, 20);

	   crossSect2=new CrossDrawer(footWidth);		   
	   bmCrossSect2=Bitmap.createBitmap(crossSect2.width,crossSect2.height,
			   							Constant.BM_CONFIG);	   

	   cvsCrossSect2=new Canvas(bmCrossSect2);   
	   cvsCrossSect2.drawBitmap(crossSect2.drawCrossV2(),0,0, pred);	
   }
   //---------------------------------------------------------------------------------------------------
   private void initFootSect3(final Ruler rulerIn){
	   
	   bmSect3=Bitmap.createBitmap(footWidth, ySect4-ySect3, Constant.BM_CONFIG);
	   cvsSect3=new Canvas(bmSect3);
	   cvsSect3.drawColor(Constant.RBRRB_RED);
	   
	   bmSect3a=Bitmap.createBitmap(bmSect3.getWidth(), bmSect3.getHeight(), Constant.BM_CONFIG);
	   cvsSect3a=new Canvas(bmSect3a);
	   cvsSect3a.drawColor(Constant.RBRRB_RED);
	   
	   final int x3a=(bmSect3a.getWidth()-gridWidth)/2,
			   y3a=(bmSect3a.getHeight()-gridWidth)/2;
	   
	   Paint pp=new Paint();
	   pp.setColor(Constant.RBRRB_RED);
	   
	   cvsSect3a.drawBitmap(
			   crossSect1.getCross(),
			   new Rect(0,0, gridWidth, gridWidth),
			   new Rect(x3a, y3a,
					   	x3a+gridWidth, y3a+gridWidth), 
			   paint);	      	   	   
	
	   
   }
   //---------------------------------------------------------------------------------------------------               
   private void initSect4wall(final Ruler rulerIn){
	   //
	   //		init section 4 main bitmap
	   //
	   bmSect4=Bitmap.createBitmap(   footWidth*3,  
			   						getSect4height(rulerIn.pxHeight),  
			   						Constant.BM_CONFIG);
	   cvsSect4=new Canvas(bmSect4);	   	   
	   cvsSect4.drawColor(Constant.RBRRB_BLACK);	   
	   //
	   //	section 4a region flickering alternatives
	   //
	   bmSect4a=Bitmap.createBitmap(  	footWidth-gridWidth*2, 
			   							getSect4height(rulerIn.pxHeight), 
			   							Constant.BM_CONFIG);
	   cvsSect4a=new Canvas(bmSect4a);
	   cvsSect4a.drawColor(Constant.RBRRB_BLACK);
	   if(which.equals(Constant.FOOT_RIGHT))	 
		   xSect4a=0;
	   else
		   xSect4a=bmSect4.getWidth()-bmSect4a.getWidth();
			   
	   //
	   //	section 4a cross
	   //
	   bmSect4cross=Bitmap.createBitmap(
			   bmCrossSect2.getWidth(),
			   bmCrossSect2.getHeight(), 
			   Constant.BM_CONFIG);
	   
	   cvsSect4cross=new Canvas(bmSect4cross);	     
	   if(which.equals(Constant.FOOT_RIGHT))	 
		   xSect4cross=footWidth-gridWidth;
	   else
		   xSect4cross=(footWidth-gridWidth)*2;
	   //
	   //	section 4b region flickering alternatives
	   //
	   bmSect4b=Bitmap.createBitmap(  	gridWidth, 
			   							getSect4height(rulerIn.pxHeight), 
			   							Constant.BM_CONFIG);
	   cvsSect4b=new Canvas(bmSect4b);
	   cvsSect4b.drawColor(Constant.RBRRB_RED);	  	   
	   //
	   //	EOR bar init
	   //
	   cir=new CircleDrawer(gridWidth);	   
	   
	   bmSect4bar=Bitmap.createBitmap(
			   				Math.round(cir.getScaledCirWidth()), 
			   				bmSect4.getHeight(),
			   				Constant.BM_CONFIG
			   			);
	   
	   cvsSect4bar=new Canvas(bmSect4bar);
	   cvsSect4bar.drawColor(Constant.RBRRB_BLACK);	   
		   //
	   //	bar position
	   //
	   if(which.equals(Constant.FOOT_RIGHT))	   		
	   		xSect4bar=bmSect4.getWidth()-100;   // x position of bar @ bmSect4
	   else
		    xSect4bar=100+bmSect4bar.getWidth();	   
	   //
	   //	prepare section 4a main bitmap
	   //	   
	   int x4a,x4b; 
	   if(which.equals(Constant.FOOT_RIGHT)){
		   x4a=0;
		   x4b=xSect4cross-gridWidth;
	   }else{
		   x4a=xSect4cross+bmSect4cross.getWidth()+gridWidth;
		   x4b=bmSect4.getWidth();
	   }
   	   cvsSect4.drawRect(  // simple red color rectangle
   						new Rect(
		   				x4a,0,
		   				x4b,bmSect4.getHeight()), 
		   				pred);	   		
	   //
	   //		draw section 4b normal
	   //
   		int cxFrom, cxTo;
   		if(which.equals(Constant.FOOT_RIGHT)){
   			cxFrom=xSect4cross-gridWidth;
   			cxTo=xSect4cross;
   		}else{
   		    cxFrom=xSect4cross+bmSect4cross.getWidth();
   		    cxTo=cxFrom+gridWidth;	   		    
   		}

		
   		for(int cx=cxFrom;  cx+cir.getScaledCirWidth() <= cxTo ; cx+=cir.getScaledCirWidth() ){   		
   		//for(int cx=cxFrom;  cx <= cxTo ; cx+=cir.getScaledCirWidth() ){
   			for(int cy=0; cy < bmSect4.getHeight(); cy+=cir.getScaledCirWidth() ){
   				cir.draw(cvsSect4, cx, cy);
   			}
   		}	   	
   		//
   		//		draw Section 4b alternatives
   		//
		xSect4b=cxFrom;
   		for(int cx=0;  cx+cir.getScaledCirWidth() <= bmSect4b.getWidth() ; cx+=cir.getScaledCirWidth() ){   		
   			for(int cy=2; cy < bmSect4.getHeight(); cy+=cir.getScaledCirWidth() ){
   				cir.drawRev(cvsSect4b, cx, cy);
   			}
   		}			
  		//
  		// 		Section 4c + 4d  
   		//
   		int x4dFrom, x4dTo;
   		if(which.equals(Constant.FOOT_RIGHT)){
   			x4dFrom=bmSect4.getWidth()-Math.round(cir.getScaledCirWidth());
   			x4dTo=xSect4cross+bmSect4cross.getWidth();
   		}else{
   			x4dFrom=xSect4cross-Math.round(cir.getScaledCirWidth());
   			x4dTo=0;
   		}   		
	   		
		for(int cx=x4dFrom; cx >= x4dTo; cx-=Math.round(cir.getScaledCirWidth())){
		   for(int cy=0; cy < bmSect4.getHeight(); cy+=Math.round(cir.getScaledCirWidth())){
			   cir.draw(cvsSect4, cx, cy);
		   }
		}
	

   }
   private void initFootSect4(final Ruler rulerIn){ 
	   crossSect4=new CrossDrawer(footWidth);
	   cvsSect4cross.drawBitmap(crossSect4.drawCrossV2(),0,0, pred);
   
	   final int vmax=bmSect4.getHeight()-bmSect4cross.getHeight();
	   final int vmin=0;
	   final int vtick=10; // 10 ticks to swipe from min to max
   
	   swipeVsect4=new SwipeVertical(rulerIn, vmin, vmax, vtick );
   
	   //
	   //	section 4 swipe horizon Control
	   //
   
	   int v4min, v4max, v4mid;
	   if( which.equals(Constant.FOOT_LEFT)){
		   v4min=footWidth - bmSect4.getWidth();
		   v4max=0;	
		   v4mid=(footWidth/2)-(xSect4cross+bmSect4cross.getWidth()/2);
	   }else{
		   v4min=footWidth - bmSect4.getWidth();
		   v4max=0;// max is kept @ right-most boundary in order to see whole sect4a
		   v4mid=(footWidth/2)-(xSect4cross+bmSect4cross.getWidth()/2);		   
	   }
   
	   Log.d("swipe", 
		   v4min+","+v4mid+","+v4max+"    "+ 
		   (which.equals(Constant.FOOT_LEFT)?"left":"right"));
   
	   Log.d("swipe",bmSect4.getWidth()+" > "+footWidth);
   
	   swipeHorizonSect4= new SwipeHorizon(
		   			v4min, v4max, v4mid,
		   			50,  //step; default 5
		   			gridWidth,
		   			which
		   			);	 

   }
   //---------------------------------------------------------------------------------------------------
   public void drawFoot(final int yOffset, Canvas cc){

	   flickSect1.update();	   
	   flickSect1c.update();	   
	   flickSect2.update();
	   flickSect3.update();
	   flickSect4bar.update();
	   //
	   //	draw sections
	   //
	   drawFootSect2(cc, yOffset); 
	   // 	since cross in sect2 will draw beyond sect2 boundary,
	   //	sect2 is drawn b4 sect1 and sect3, too lazy to modify code...
	   drawFootSect1(cc, yOffset);	  	   
	   drawFootSect3(cc, yOffset);	   
	   drawFootSect4(cc, yOffset);	 	   	   

   }
   
   //---------------------------------------------------------------------------------------------------       
   private int getSect4height(final int scrHeight){

	   return footWidth*4;
	   

   }
   //---------------------------------------------------------------------------------------------------             
   private int getSect2redWidth(final int footww){
	   return Math.round( (footww - gridWidth)/2);
   }
   //---------------------------------------------------------------------------------------------------                   
   private void drawFootSect1(Canvas cc, final int yOffset){
	   
	   int xx=0;
	   if( flickSect1c.isTrue()){
		   xx=gridWidth;		   
	   }  
	   cvsSect1.drawBitmap(bmGridSect1, 
			   new Rect(xx,0,xx+footWidth,bmSect1.getHeight()), // subset of bmGridSect1
			   new Rect(0,0,footWidth,bmSect1.getHeight()),				   
			   paint);	  	   
   
   		cc.drawBitmap(bmSect1,  				
		   		new Rect(0,0, bmSect1.getWidth(), bmSect1.getHeight()), // bmsect1 
		   		new Rect(pos.x, ySect1+yOffset,pos.x+footWidth, ySect1+yOffset+bmSect1.getHeight()), 
		   		paint);   		
   }
   //---------------------------------------------------------------------------------------------------             
   private void drawFootSect2(Canvas cc, final int yOffset){
	   //
	   //	draw sect2
	   //
	   cc.drawBitmap(
		   		bmSect2,
		   		new Rect(	0,0, 
		   					bmSect2.getWidth(), 
		   					bmSect2.getHeight()),  
		   		new Rect(	pos.x, 
		   					yOffset+ySect2, 
		   					pos.x+bmSect2.getWidth(), 
		   					yOffset+ySect2+bmSect2.getHeight()), 
		   		paint);  	 	   
	   
	   
	   
	   final int x2=pos.x + (bmSect2.getWidth()-bmCrossSect2.getWidth())/2;
	   final int y2=yOffset+ySect2+swipeVsect2.pos;
	   //
	   //	draw cross
	   //
	   cc.drawBitmap(	bmCrossSect2, 
				new Rect(0,0, bmCrossSect2.getWidth(), bmCrossSect2.getHeight()), // bmcrosssect2   				
				new Rect(x2, y2, x2+bmCrossSect2.getWidth(), y2+bmCrossSect2.getHeight()),
 				paint
			   );	   

	   swipeVsect2.next();
	   //
	   //	draw red color wall (bmSect2red) on both sides of cross
	   //   note: part of cross will be overlapped by it
	   //
	   if( flickSect2.isTrue()){   	  		
  	  		cc.drawBitmap(
			   		bmSect2red,
			   		new Rect(	0,0, 
			   					bmSect2red.getWidth(), 
			   					bmSect2red.getHeight()),  
			   		new Rect(	pos.x, 
			   					yOffset+ySect2, 
			   					pos.x+bmSect2red.getWidth(), 
			   					yOffset+ySect2+bmSect2red.getHeight()), 
			   		paint);  
			   
  	  		cc.drawBitmap(
		   			bmSect2red,
		   			new Rect(	0,0, 
		   						bmSect2red.getWidth(), 
		   						bmSect2red.getHeight()),  
		   			new Rect(	pos.x+ (footWidth-bmSect2red.getWidth()), 
		   						yOffset+ySect2, 
		   						footWidth+pos.x, 
		   						yOffset+ySect2+bmSect2red.getHeight()), 
		   			paint); 	   
	   
  	  	}
   }
   //---------------------------------------------------------------------------------------------------                   
   private void drawFootSect3(Canvas cc, final int yOffset){
	   cc.drawBitmap(			   
			   flickSect3.isTrue() ? bmSect3a : bmSect3,
			   new Rect(0,0, bmSect3.getWidth(), bmSect3.getHeight()),
			   new Rect( 
					   pos.x, 
					   yOffset+ySect3, pos.x+footWidth, 
					   yOffset+ySect3+bmSect3.getHeight()), 
			   paint);
   }

   //---------------------------------------------------------------------------------------------------                         
   private void drawFootSect4(Canvas cc, final int yOffset){	  	   	   
	   mapInfo=new Sect4Mapping();
	   //
	   // draw Sect4 sprite main graphics
	   //
	   mapInfo=mapSect4toFoot(  
			   		mapInfo,
		   			new Rect(0,0,
		   					bmSect4.getWidth(),
		   					bmSect4.getHeight()
		   					), 
		   			-swipeHorizonSect4.getPos(), 
		   			-yOffset,  
		   			ySect4
		   			);	   
	   if(mapInfo.seen){
	    cc.drawBitmap(  
	    		bmSect4,   				
   				mapInfo.rectSrc,
				mapInfo.rectDest,
   				paint);
	   }
	   //
	   //	draw sect4a 4b Flickering Alternative
	   //
	   if( !flickSect2.isTrue() ){ // sect4a sync with sect2 flickering
		   //
		   //	sect4a
		   //
		   mapInfo=new Sect4Mapping();
		   mapInfo=mapSect4toFoot(  mapInfo,
		   				new Rect(xSect4a,0,
		   					xSect4a+bmSect4a.getWidth(),
		   					bmSect4a.getHeight()
		   					), 		
			   			-swipeHorizonSect4.getPos(), 
			   			-yOffset,  
			   			ySect4
			   			);	
	  	   
		   if(mapInfo.seen){		   
			   cc.drawBitmap( 			   				
					   bmSect4a,
					   mapInfo.rectSrc,
					   mapInfo.rectDest,
					   paint);	   
	   			}
		   //
		   //	sect4b
		   //
		   mapInfo=new Sect4Mapping();
		   mapInfo=mapSect4toFoot(  mapInfo,
	   				new Rect(xSect4b,0,
	   					xSect4b+bmSect4b.getWidth(),
	   					bmSect4b.getHeight()
	   					), 		
		   			-swipeHorizonSect4.getPos(), 
		   			-yOffset,  
		   			ySect4
		   			);	
 	   
	   if(mapInfo.seen){		   
		   cc.drawBitmap( 			   				
				   bmSect4b,
				   mapInfo.rectSrc,
				   mapInfo.rectDest,
				   paint);	   
  			}		   
	   }	   	   	   
	   //
	   //	draw EOR bar @ sect4d
	   //
	   if( flickSect4bar.isTrue() ){	
		   mapInfo=new Sect4Mapping();
		   mapInfo=mapSect4toFoot(  mapInfo,
			   			new Rect(
			   					xSect4bar,0,
			   					xSect4bar+bmSect4bar.getWidth(),
			   					bmSect4bar.getHeight()
			   					), 
			   			-swipeHorizonSect4.getPos(), 
			   			-yOffset,  
			   			ySect4
			   			);	
	  	   
		   if(mapInfo.seen){		   
			   cc.drawBitmap( 			   				
					   bmSect4bar,
					   mapInfo.rectSrc,
					   mapInfo.rectDest,
					   paint);	   
	   			}
	   }
	   

	   //
	   //	draw cross
	   //			   				
	   final int 	xOffset=xSect4cross, 
			   		vOffset=swipeVsect4.pos;
	   mapInfo=new Sect4Mapping();
	   mapInfo=mapSect4toFoot(  mapInfo,
  					   			new Rect(xOffset,vOffset,
					   					xOffset+bmSect4cross.getWidth(),
   					   					vOffset+bmSect4cross.getHeight()
   					   					), 
   					   			-swipeHorizonSect4.getPos(), 
   					   			-yOffset,  
   					   			ySect4);	
			   			   
	   if(mapInfo.seen){			
		 			   cc.drawBitmap( 			   				
		   		   				bmSect4cross,
		   		   				mapInfo.rectSrc,
		   						mapInfo.rectDest,
		   		   				paint);		   		   					   
		   			   }	
	   swipeVsect4.next();
	   swipeHorizonSect4.next();
   }
   
   //---------------------------------------------------------------------------------------------------                               
   private Sect4Mapping mapSect4toFoot(
		   Sect4Mapping sect4map,
		   final Rect src, 
		   final int sx, final int sy,  final int oy){
	   //
	   //	translate Rect() info as Rect source and Rect destination
	   //	   
	   sect4map=new Sect4Mapping();
	   sect4map.rectSrc=new Rect();
	   sect4map.rectDest=new Rect();
	   
	   int x1s, x2s, y1s, y2s;	   
	   //
	   //	find Rect Source X-axis
	   //		   
	   x1s=0;  	   
	   x2s=src.right-src.left;
	   
	   if(sx > src.right){ 
		   sect4map.seen=false;
		   return(sect4map);
	   }else
		   if(sx > src.left ){ // cannot cover the whole width 
			   x1s=sx-src.left;
		   }		   

	   if(sx+footWidth < src.left){
		   sect4map.seen=false;
		   return(sect4map);
	   }
	   //
	   //	find Rect Source Y-axis
	   //	
	   //
	   if(src.top+oy-sy > footHeight){
		   sect4map.seen=false;
		   return(sect4map);
	   }else
		   if(src.top+oy-sy >= 0){ 	//case b: seen from the top
			   y1s=0;			   
			   y2s=footHeight - (src.top+oy-sy);
		   }else{ 					// case c: whole height is seen
			   y1s=-(src.top+oy-sy);
			   y2s=y1s+footHeight;			  
		   }

   	  	if(x2s-x1s > footWidth){

	   		x2s=footWidth+x1s;
   	  	} 	     
	   //
	   //	final Rect Destination
	   //
	   int x1d, y1d;  // x2d, y2d;
	   
	   x1d= src.left-sx;

	   if(sx > src.left){
		   x1d=0;
	   }
	   
	   // new checkpoint
	   if(x1d+x2s-x1s > footWidth){
		   x2s=footWidth+x1s-x1d;
	   }
	   	   
	   y1d=src.top+oy-sy;
	   if( oy-sy+src.top < 0 ){ // top not seen
		   y1d=0;
	   }
	   sect4map.rectSrc=new Rect(x1s, y1s, x2s, y2s);
	   sect4map.rectDest=new Rect(pos.x+x1d, y1d, pos.x+x1d+(x2s-x1s), y1d+(y2s-y1s));
	   sect4map.seen=true;
	   	   
	   return sect4map;
   }
}
