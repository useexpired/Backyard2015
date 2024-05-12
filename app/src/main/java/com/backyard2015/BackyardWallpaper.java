package com.backyard2015;


import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
//-------------------------------------------------------
//
//  		Backyard 2015 
//			Wallpaper Project
//
//-------------------------------------------------------
public class BackyardWallpaper extends WallpaperService {

    @Override    public void onCreate() {        super.onCreate();     	}
    @Override    public void onDestroy() {       super.onDestroy();    	}
    @Override    public Engine onCreateEngine() {return new TheEngine();}
    // -----------------------------------------------------
    //
    // 		Engine class
    //
    // -----------------------------------------------------    
    class TheEngine extends Engine {


        public Ruler ruler; 	// display metrics

        Boolean screenVisible = false;

        private Footstep footstep;
        private Foot footLeft, footRight;
        Boolean runThreadMain=true;

        // ----------------------------------------------------------
        public TheEngine() {

            ruler=new Ruler(getApplicationContext());


            footstep=new Footstep(ruler.pxHeight);
            final int barH=Constant.statusBarHeight(getResources());
            footLeft=new Foot(ruler, Constant.FOOT_LEFT, barH);
            footRight=new Foot(ruler, Constant.FOOT_RIGHT, barH);
            //
            //	thread Main
            //
            Thread threadMain = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (runThreadMain) {
                        try {
                            Thread.sleep(Constant.ETERNAL_TICK_DELAY);
                            handleThreadMain.sendMessage(handleThreadMain.obtainMessage());
                        } catch (InterruptedException iex) {}

                        drawSurface();
                    }
                }
            });
            threadMain.start();
        }
        // -----------------------------------------------------
        Handler handleThreadMain = new Handler() {
            @Override
            public void handleMessage(Message msg) {}
        };
        //-----------------------------------------------------------
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(false);
        }
        //-----------------------------------------------------------
        @Override
        public void onDestroy() {
            super.onDestroy();
            cleanThreads();
        }
        //-----------------------------------------------------------
        @Override
        public void onVisibilityChanged(boolean visible) {
            screenVisible = visible;
            if (visible) {
                drawSurface();
            } else {
                //runThreadMain=false;                                             
            }
        }
        //-----------------------------------------------------------
        @Override
        //
        //	cannot lock orientation to portrait @ manifest.xml,
        //	as <service> can't be locked like <activity>
        //
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            drawSurface();
        }
        //-----------------------------------------------------------
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }
        //-----------------------------------------------------------
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            screenVisible = false;
            cleanThreads();
        }
        //-----------------------------------------------------------
        @Override
        public void onOffsetsChanged(	float xOffset, float yOffset, float xStep,
                                         float yStep, int xPixels, int yPixels) {
            drawSurface();
        }

        // ------------------------------------------------------------------
        public void drawSurface() {
            //
            //	refresh rate of drawSurface() = constant.interval_surface
            //
            final SurfaceHolder holder = getSurfaceHolder();
            Canvas cc = null;
            try {
                cc = holder.lockCanvas();
                if (cc != null) {
                    drawSprite(cc);
                    footstep.update();
                }
            } finally {
                if (cc != null)
                    holder.unlockCanvasAndPost(cc);
            }
        }
        // -----------------------------------------------------------------        
        private void drawSprite(Canvas cc) {

            cc.save();

            footLeft.drawFoot(footstep.getYleft(), cc);
            footRight.drawFoot(footstep.getYright(), cc);

            cc.restore();
        }

        //-----------------------------------------------------------          
        private void cleanThreads(){

            runThreadMain=false;
            System.gc();
        }
    }

}