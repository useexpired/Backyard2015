package com.backyard2015;

public class Flicker {
	private Boolean flag;
	private int ii, max;
	public Flicker(int maxin){
		flag=true;
		ii=0;
		max=maxin;
	}
	public void update(){
		ii++;
		if(ii >= max){
			flag=!flag;
			ii=0;
		}
	}
	public Boolean isTrue(){
		return flag;
	}
}
