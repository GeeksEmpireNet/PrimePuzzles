package net.geeksempire.primepuzzles.Utils.UI;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class SwipeGestureFilter extends SimpleOnGestureListener {

    public final static int SWIPE_UP = 1;
    public final static int SWIPE_DOWN = 2;
    public final static int SWIPE_LEFT = 3;
    public final static int SWIPE_RIGHT = 4;

    public final static int MODE_TRANSPARENT = 0;
    public final static int MODE_SOLID = 1;
    public final static int MODE_DYNAMIC = 2;

    private final static int ACTION_FAKE = -666;

    private int swipe_Min_Distance = 10;
    private int swipe_Max_Distance = 1000;

    private int swipe_Min_Velocity = 10;

    private int mode = MODE_DYNAMIC;

    private boolean running = true;

    private boolean tapIndicator = false;

    private View view;
    private GestureDetector gestureDetector;
    private GestureListener gestureListener;

    public SwipeGestureFilter(View view, Context context, GestureListener gestureListener) {
        this.view = view;
        this.gestureDetector = new GestureDetector(context, this);
        this.gestureListener = gestureListener;
    }

    public void onTouchEvent(MotionEvent event) {

        if (!this.running) {
            return;
        }

        boolean result = this.gestureDetector.onTouchEvent(event);
        if (this.mode == MODE_SOLID) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        else if (this.mode == MODE_DYNAMIC) {
            if (event.getAction() == ACTION_FAKE) {
                event.setAction(MotionEvent.ACTION_UP);
            }
            else if (result) {
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
            else if (this.tapIndicator) {
                event.setAction(MotionEvent.ACTION_DOWN);
                this.tapIndicator = false;
            }
        }
        //Else -> Just Do Nothing | MODE_TRANSPARENT
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setEnabled(boolean status) {
        this.running = status;
    }

    public int getSwipeMaxDistance() {
        return this.swipe_Max_Distance;
    }

    public void setSwipeMaxDistance(int distance) {
        this.swipe_Max_Distance = distance;
    }

    public int getSwipeMinDistance() {
        return this.swipe_Min_Distance;
    }

    public void setSwipeMinDistance(int distance) {
        this.swipe_Min_Distance = distance;
    }

    public int getSwipeMinVelocity() {
        return this.swipe_Min_Velocity;
    }

    public void setSwipeMinVelocity(int distance) {
        this.swipe_Min_Velocity = distance;
    }

    @Override
    public boolean onFling(MotionEvent downMotionEvent, MotionEvent moveMotionEvent, float velocityX, float velocityY) {

        final float xDistance = Math.abs(downMotionEvent.getX() - moveMotionEvent.getX());
        final float yDistance = Math.abs(downMotionEvent.getY() - moveMotionEvent.getY());

        if (xDistance > this.swipe_Max_Distance || yDistance > this.swipe_Max_Distance) {
            return false;
        }

        velocityX = Math.abs(velocityX);
        velocityY = Math.abs(velocityY);

        boolean result = false;
        if ((velocityY >= this.swipe_Min_Velocity) && (yDistance > this.swipe_Min_Distance) && (xDistance < yDistance)) {//Vertical
            if (downMotionEvent.getY() > moveMotionEvent.getY()) {//Bottom -> Up
                this.gestureListener.onSwipe(SWIPE_UP);

                /*view.animate()
                    .x(downMotionEvent.getRawX() + xDistance)
                        .y(downMotionEvent.getRawY() + yDistance)
                        .setDuration(0)
                        .start();*/
            } else {//Up -> Bottom
                this.gestureListener.onSwipe(SWIPE_DOWN);
            }
            result = true;
        }

        if ((velocityX >= this.swipe_Min_Velocity) && (xDistance > this.swipe_Min_Distance) && (yDistance < xDistance)) {//Horizontal
            if (downMotionEvent.getX() > moveMotionEvent.getX()) {//Right -> Left
                this.gestureListener.onSwipe(SWIPE_LEFT);
            } else {//Left -> Right
                this.gestureListener.onSwipe(SWIPE_RIGHT);
            }
            result = true;
        }
        return result;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        this.gestureListener.onSingleTapUp();

        return false;
    }

    public interface GestureListener {
        void onSwipe(int direction);

        void onSingleTapUp();
    }
}
