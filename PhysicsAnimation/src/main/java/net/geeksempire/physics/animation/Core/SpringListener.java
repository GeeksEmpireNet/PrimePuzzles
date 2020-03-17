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

public interface SpringListener {

  /**
   * called whenever the spring is updated
   * @param spring the Spring sending the update
   */
  void onSpringUpdate(Spring spring);

  /**
   * called whenever the spring achieves a resting state
   * @param spring the spring that's now resting
   */
  void onSpringAtRest(Spring spring);

  /**
   * called whenever the spring leaves its resting state
   * @param spring the spring that has left its resting state
   */
  void onSpringActivate(Spring spring);

  /**
   * called whenever the spring notifies of displacement state changes
   * @param spring the spring whose end state has changed
   */
  void onSpringEndStateChange(Spring spring);
}

