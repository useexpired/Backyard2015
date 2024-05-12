package com.backyard2015;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
//
//		draw a circle
//
public class CircleDrawer {

	private Paint paint, paintInv;
	private RectF oval;
	private float scale, offset1, offset2;
	private float scaledCirWidth;

	//---------------------------------------------------------------------------------------------------
	public CircleDrawer(final int gridW){

		oval=new RectF();
		scale=	(float)gridW /
				(float)(gridW - (gridW % Constant.CIRCLE_WIDTH));

		offset1=scale*3.0f;
		offset2=scale*(3f+9f);

		scaledCirWidth=scale*Constant.CIRCLE_WIDTH;

		paint=new Paint();
		paint.setColor(Constant.RBRRB_RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(4*scale);
		paintInv=new Paint();
		paintInv.set(paint);
		paintInv.setColor(Constant.RBRRB_BLACK);



	}
	//---------------------------------------------------------------------------------------------------
	public void draw(Canvas cvs, final int x, final int y){

		oval.set(	(float)x+offset1, (float)y+offset1,
				(float)x+offset2, (float)y+offset2);

		cvs.drawArc(oval, 0, 360, true, paint);
	}
	//---------------------------------------------------------------------------------------------------
	public void drawRev(Canvas cvs, final int x, final int y){
		oval.set((float)x+offset1, (float)y+offset1, (float)x+offset2, (float)y+offset2);

		cvs.drawArc(oval, 0, 360, true, paintInv);
	}
	//---------------------------------------------------------------------------------------------------	  	
	public float getScale(){
		return scale;
	}
	//---------------------------------------------------------------------------------------------------	  		
	public float getScaledCirWidth(){
		return scaledCirWidth;
	}
}
