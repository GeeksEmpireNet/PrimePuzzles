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

public class SynchronousLooper extends SpringLooper {

  public static final double SIXTY_FPS = 16.6667;
  private double mTimeStep;
  private boolean mRunning;

  public SynchronousLooper() {
    mTimeStep = SIXTY_FPS;
  }

  public double getTimeStep() {
    return mTimeStep;
  }

  public void setTimeStep(double timeStep) {
    mTimeStep = timeStep;
  }

  @Override
  public void start() {
    mRunning = true;
    while (!mSpringSystem.getIsIdle()) {
      if (mRunning == false) {
        break;
      }
      mSpringSystem.loop(mTimeStep);
    }
  }

  @Override
  public void stop() {
    mRunning = false;
  }
}

