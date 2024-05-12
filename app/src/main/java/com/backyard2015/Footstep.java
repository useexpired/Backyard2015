package com.backyard2015;



public class Footstep{
	//
	//	Footstep: handle y-axis of left and right foot
	//
	//

    
	private Boolean moveLeft;
    private Boolean moveUp;
    //
	//	numeric ranges in pixels (NOT device independent pixel)
    //
	public int yLeft, yRight, yMin, yMax;

	private int stepUp, stepDown;
	private int yMinPause, yMaxPause;
	private final int 	PAUSE_TICK_UP=50, 	PAUSE_TICK_DOWN=20,
						TICKS_2_TOP=50, 	TICKS_2_BOTTOM=10;	
	//-----------------------------------------------------------
    public Footstep(final int scrHeightPx){ 
    	
            stepUp=Constant.getStep(scrHeightPx, this.TICKS_2_TOP); 		// step=15px for 800px height; 50steps as base
            stepDown=Constant.getStep(scrHeightPx, this.TICKS_2_BOTTOM);	// 10 steps to drop down to bottom 
            
            moveLeft=true;
            moveUp=true;
            yMin= -scrHeightPx;  
            yMax=0;
            yLeft=0; yRight=0;
            
            yMinPause=yMin - (stepUp * this.PAUSE_TICK_UP);
            yMaxPause=yMax + (stepDown * this.PAUSE_TICK_DOWN);                                   
	}
	//-----------------------------------------------------------    
    public String debugReport(){
    	String str;
    	str="foot="+yLeft+" "+yRight;
    	return str;
    }
	//-----------------------------------------------------------        
    public int getYleft(){
    	int yy;
    	yy=yLeft < yMin ? yMin : yLeft;
    	yy= yy > yMax ? yMax : yy;    	
    	return (yy);
    }
	//-----------------------------------------------------------        
    public int getYright(){
    	int yy;
    	yy=yRight < yMin ? yMin : yRight;
    	yy= yy > yMax ? yMax : yy;    	
    	return (yy);
    }  
    //-----------------------------------------------------------
	public void update(){
		//
		//	left foot
		//
		if(moveLeft && moveUp ){			
			yLeft-=stepUp;			
			if(yLeft <= yMinPause){ 
				yLeft=yMin;
				moveUp=false;
			}
		}else
		if(moveLeft && !moveUp){
			yLeft+= stepDown;
			if(yLeft >= yMaxPause){ 
				yLeft=yMax;
				moveUp=true;
				moveLeft=false;
			}			
		}else
		//
		//  right foot
		//
		if(!moveLeft && moveUp){
			yRight-= stepUp;
			if(yRight <= yMinPause){
				yRight=yMin;
				moveUp=false;
			}			
		}else
		if(!moveLeft && !moveUp){
			yRight+= stepDown;
			if(yRight >= yMaxPause){
				yRight=yMax;				
				moveUp=true;
				moveLeft=true;
			}			
		}		

	}
}
