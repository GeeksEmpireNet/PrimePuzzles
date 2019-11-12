/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
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

