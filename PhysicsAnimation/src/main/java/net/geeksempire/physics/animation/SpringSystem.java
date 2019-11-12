/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
 */

package net.geeksempire.physics.animation;

import net.geeksempire.physics.animation.Core.BaseSpringSystem;
import net.geeksempire.physics.animation.Core.SpringLooper;

/**
 * This is a wrapper for BaseSpringSystem that provides the convenience of automatically providing
 * the AndroidSpringLooper dependency in {@link SpringSystem#create}.
 */
public class SpringSystem extends BaseSpringSystem {

  /**
   * Create a new SpringSystem providing the appropriate constructor parameters to work properly
   * in an Android environment.
   * @return the SpringSystem
   */
  public static SpringSystem create() {
    return new SpringSystem(AndroidSpringLooperFactory.createSpringLooper());
  }

  private SpringSystem(SpringLooper springLooper) {
    super(springLooper);
  }

}