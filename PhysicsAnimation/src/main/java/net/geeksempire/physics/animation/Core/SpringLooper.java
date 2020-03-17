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

/**
 * The spring looper is an interface for implementing platform-dependent run loops.
 */
public abstract class SpringLooper {

  protected BaseSpringSystem mSpringSystem;

  /**
   * Set the BaseSpringSystem that the SpringLooper will call back to.
   * @param springSystem the spring system to call loop on.
   */
  public void setSpringSystem(BaseSpringSystem springSystem) {
    mSpringSystem = springSystem;
  }

  /**
   * The BaseSpringSystem has requested that the looper begins running this {@link Runnable}
   * on every frame. The {@link Runnable} will continue running on every frame until
   * {@link #stop()} is called.
   * If an existing {@link Runnable} had been started on this looper, it will be cancelled.
   */
  public abstract void start();

  /**
   * The looper will no longer run the {@link Runnable}.
   */
  public abstract void stop();
}