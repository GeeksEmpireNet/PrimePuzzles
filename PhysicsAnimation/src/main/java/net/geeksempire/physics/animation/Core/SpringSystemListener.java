/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
 */

package net.geeksempire.physics.animation.Core;

/**
 * SpringSystemListener provides an interface for listening to events before and after each Physics
 * solving loop the BaseSpringSystem runs.
 */
public interface SpringSystemListener {

  /**
   * Runs before each pass through the physics integration loop providing an opportunity to do any
   * setup or alterations to the Physics state before integrating.
   * @param springSystem the BaseSpringSystem listened to
   */
  void onBeforeIntegrate(BaseSpringSystem springSystem);

  /**
   * Runs after each pass through the physics integration loop providing an opportunity to do any
   * setup or alterations to the Physics state after integrating.
   * @param springSystem the BaseSpringSystem listened to
   */
  void onAfterIntegrate(BaseSpringSystem springSystem);
}

