package com.backyard2015;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//
//	Cross Class
//
public class CrossDrawer {
		public int width, height;

		private int gridWidth, gridHeight;
		private Bitmap bmCross;
		private Canvas cvsCross;
		private Paint paint=new Paint();
		private CircleDrawer circleRed;


		//-----------------------------------------------------
		public CrossDrawer(final int footww){
			
			gridWidth=Constant.getCrossGridWidth(footww);
			gridHeight=gridWidth;
			width=gridWidth*3;
			height=width;
			
			bmCross=Bitmap.createBitmap(width, height, Constant.BM_CONFIG);
			cvsCross=new Canvas(bmCross);
			
			circleRed=new CircleDrawer(gridWidth);
			
			paint.setColor(Constant.RBRRB_RED);
			paint.setStyle(Paint.Style.STROKE);			
			paint.setStrokeWidth(4);
		}		
		//-----------------------------------------------------		
		public Bitmap drawCrossV2(){		
			int x, y;

			x=(width-gridWidth)/2;	y=0;
			drawGrid(cvsCross,x,y);
			
			x=0; y=(height-gridHeight)/2;
			drawGrid(cvsCross,x,y);	
			
			x=width-gridWidth;  y=(height-gridHeight)/2;
			drawGrid(cvsCross,x,y);
			
			x=(width-gridWidth)/2; y=height-gridHeight;
			drawGrid(cvsCross,x,y);		
			
			return bmCross;
			
		}
		//-----------------------------------------------------		
		public void drawGrid(Canvas cvs, int xi, int yi){
			
			float sWidth=circleRed.getScale()*(float)Constant.CIRCLE_WIDTH;
			
			for(int x=xi; x+sWidth <= xi+gridWidth; x+= sWidth){

				for(int y=yi; y+sWidth <= yi+gridHeight; y+=sWidth){
					circleRed.draw(cvs,x,y);
				}
			}				
		}					
		//-----------------------------------------------------		
		public Bitmap drawGrid(int xi, int yi){

			final float sWidth=circleRed.getScaledCirWidth();	
			
			for(int x=xi; x+sWidth <= xi+gridWidth; x+= sWidth){
				
				for(int y=yi; y+sWidth <= yi+gridHeight; y+=sWidth){
					circleRed.draw(cvsCross,x,y);
				}
			}		
			return bmCross;
		}
		//-----------------------------------------------------		
		public Bitmap drawGridRev(int xi, int yi){
			//
			//	draw reverse color
			//
			final float sWidth=circleRed.getScaledCirWidth();
			
			for(int x=xi; x+sWidth <= xi+gridWidth; x+= sWidth){
				
				for(int y=yi; y+sWidth <= yi+gridHeight; y+=sWidth){
					circleRed.drawRev(cvsCross,x,y);
				}
			}		
			return bmCross;
		}					
				
		//-----------------------------------------------------		
		public Bitmap drawCross(){

			int x, y;
			
			x=(width-gridWidth)/2;			y=0;
			drawCircles(cvsCross,x,y);

			x=0; y=(height-gridHeight)/2;
			drawCircles(cvsCross,x,y);			

			x=width-gridWidth;  y=(height-gridHeight)/2;
			drawCircles(cvsCross,x,y);

			x=(width-gridWidth)/2; y=height-gridHeight;
			drawCircles(cvsCross,x,y);			

			
			return bmCross;
		}
		//-----------------------------------------------------		
		public void drawCircles(Canvas cvs,  int x, int y){				
			circleRed.draw(cvs, x,  y);
			circleRed.draw(cvs, x+Constant.CIRCLE_WIDTH, y);
			circleRed.draw(cvs, x, y+Constant.CIRCLE_WIDTH);				
			circleRed.draw(cvs, x+Constant.CIRCLE_WIDTH, y+Constant.CIRCLE_WIDTH);				
		}
	
	//-----------------------------------------------------
	public Bitmap getCross(){ return bmCross;}
}
