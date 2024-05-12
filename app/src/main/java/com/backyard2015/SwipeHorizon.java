package com.backyard2015;
//
//	SwipeHorizon Class : 
//	Control Horizontal Swipe 
//
public class SwipeHorizon {
           
	private String 	whichFoot;
	private StateCtrl stateCtrl;		
	//-----------------------------------------------------------  	
	public SwipeHorizon(final int min, final int max, final int mid, final int step, final int gridWidth, 
						final String whichFoottt) {
		
		whichFoot	= whichFoottt;
		stateCtrl=new StateCtrl(min,max, mid);	
	}

	//-----------------------------------------------------------
	public int next(){
		
		switch(stateCtrl.getStatus()){
			case StateCtrl.STATE_SWIPE:
									stateCtrl.nextStep();
									break;
			case StateCtrl.STATE_PAUSE:
			default :
									stateCtrl.nextPause();
									break;			
		}
		
		return stateCtrl.pos;
	}
	//-----------------------------------------------------------  	
	public int getPos(){
		return stateCtrl.pos; 
	}
}
