/*
 * Copyright © 2019 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/11/19 6:49 PM
 * Last modified 11/11/19 6:48 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
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

