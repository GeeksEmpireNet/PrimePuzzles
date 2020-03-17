/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 2:03 PM
 * Last modified 3/17/20 12:52 PM
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

