package com.backyard2015;
//
//		SwipeVertical Class : 
//		Control Unit of crossFigure swipe up n down
//
public class SwipeVertical {
    
	private Boolean scrollUp;
	private int step;		
	public int min, max, pos;		
	//-----------------------------------------------------------  	
	public SwipeVertical(final Ruler rulerr, final int min, final int max, final int ticks) {

		this.scrollUp = true;
		this.min=min;
		this.max=max;
		this.pos=Utili.rand(min, max);
		this.step = Constant.getStep(max-min,ticks);
		//
		// ticks= ticks needed to move from min to max; each tick is
		//		  a thread delayed by Constant.ETERNAL_DELAY milliseconds 
		//		  ( in BackyardWallpaper Class)
		//
		//	step = pixels needed to move for each swipe
		// 
	}
	//-----------------------------------------------------------  
	public int next() {

		pos += scrollUp ? step : -step;

		if (pos >= max ){
			scrollUp = !scrollUp;
			pos=max;
			//pos -=step;
		}
		if( pos <= min){
			scrollUp=!scrollUp;
			pos=min;
		}

		      		
		return pos;
	}

}
