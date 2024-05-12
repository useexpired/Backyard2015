package com.backyard2015;

import java.util.Random;

public class Utili{

    private static final Utili instance = new Utili();
    //
    //	this getInstance() makes you need NO create classes b4 using its methods
    //
    public static Utili getInstance(){
        return instance;
    }
    private Utili(){}    
    
    // -----------------------------------------------------------------        
    public static int rand(final int min, final int max){
        Random rr = new Random();
        final int i1 = rr.nextInt(max - min + 1) + min;
        return i1;
    }

}