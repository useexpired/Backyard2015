package com.backyard2015;

//
//	StateCtrl.java
//
//	Control Module of SwipeHorizon
//
//-----------------------------------------------------------  	
public class StateCtrl{
	public static final int STATE_SWIPE=1, STATE_PAUSE=2;		
	public int pos;
	
	private State[] arrState;		
	private int destination, step;
	private int idx;
	private int pauseCtr;

	//-----------------------------------------------------------  		
	public StateCtrl(final int min, final int max, final int mid){
		//Log.d("swim",min+" < "+mid+" < "+max);
		arrState=new State[6];
		arrState[0]=new State(50,  StateCtrl.STATE_PAUSE);		// pause 50 ticks
		//	12 ticks from min to mid position
		arrState[1]=new State(mid, StateCtrl.STATE_SWIPE, Constant.getStep(mid-min, 12));	
		arrState[2]=new State(70,  StateCtrl.STATE_PAUSE);		// pause 70 ticks
		//	12 ticks from mid to max position		
		arrState[3]=new State(max,  StateCtrl.STATE_SWIPE, Constant.getStep(max-mid, 12));	
		arrState[4]=new State(50,  StateCtrl.STATE_PAUSE);		// pause 50 ticks
		//	7 ticks from min to mid position		
		arrState[5]=new State(min,  StateCtrl.STATE_SWIPE, -1*Constant.getStep(max-min, 6));		
		
		pauseCtr=0;
		pos=min;
		destination=step=0;			
		idx=arrState.length-1;
		
		this.nextState();

	}
	//-----------------------------------------------------------  		
	public void nextState(){
		idx++;
		if( idx >= arrState.length){
			idx=0;
		}	
		switch(arrState[idx].status){
			
				case  STATE_SWIPE:  this.destination=arrState[idx].to;
									this.step=arrState[idx].step;
									break;
				case  STATE_PAUSE:
									this.pauseCtr=arrState[idx].duration;
				default:
									break;
		}				
	}	
	//-----------------------------------------------------------  		
	public void nextStep(){
		this.pos+=this.step;
		if(		(this.step > 0 &&  this.pos >= this.destination)||
				(this.step < 0 &&  this.pos <= this.destination)){
			  this.pos=this.destination;
			  this.nextState();
		  }
	}
	//-----------------------------------------------------------  		
	public void nextPause(){
		this.pauseCtr--;
		if( this.pauseCtr <= 0 ){
			this.nextState();
		}
	}
	//-----------------------------------------------------------  	
	public int getStatus(){
		return this.arrState[this.idx].status;
	}
	//-----------------------------------------------------------  		
	public Boolean isPaused(){
		if ( arrState[idx].status== STATE_PAUSE){
			return true;
		}else
			return false;
	}	
	//-----------------------------------------------------------  		
	public class State{
		//
		//	embedded class
		//
		int duration;
		int from, to, step;
		int status;
		//-----------------------------------------------------------  	
		public State(final int dur, final int stat){ 
			// pause state
			duration=dur;
			status=stat;
		}
		//-----------------------------------------------------------  	
		public State(final int xto, final int stat, final int step){			 
			// swipe state
			to=xto;
			status=stat;
			this.step=step;
		}
				
	}
	//-----------------------------------------------------------  	
	public String debugReport(){
		String rr;
		rr=" idx=" +idx;
		rr+= arrState[idx].status == STATE_PAUSE ? " pause" : " swipe";
		rr+=" step="+step;
		rr+=" to="+destination;
		rr+=" pos="+pos;
		rr+="      pausectr="+pauseCtr;
		
		return rr;
	}
	
}