/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
 */

package net.geeksempire.physics.animation.Core;

public class SteppingLooper extends SpringLooper {

  private boolean mStarted;
  private long mLastTime;

  @Override
  public void start() {
    mStarted = true;
    mLastTime = 0;
  }

  public boolean step(long interval) {
    if (mSpringSystem == null || !mStarted) {
      return false;
    }
    long currentTime = mLastTime + interval;
    mSpringSystem.loop(currentTime);
    mLastTime = currentTime;
    return mSpringSystem.getIsIdle();
  }

  @Override
  public void stop() {
    mStarted = false;
  }
}

