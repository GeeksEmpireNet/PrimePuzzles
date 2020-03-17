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
 * Helper math util to convert tension &amp; friction values from the Origami design tool to values
 * that the spring system needs.
 */
public class OrigamiValueConverter {

  public static double tensionFromOrigamiValue(double oValue) {
    return oValue == 0 ? 0 : (oValue - 30.0) * 3.62 + 194.0;
  }

  public static double origamiValueFromTension(double tension) {
    return tension == 0 ? 0 : (tension - 194.0) / 3.62 + 30.0;
  }

  public static double frictionFromOrigamiValue(double oValue) {
    return oValue == 0 ? 0 : (oValue - 8.0) * 3.0 + 25.0;
  }

  public static double origamiValueFromFriction(double friction) {
    return friction == 0 ? 0 : (friction - 25.0) / 3.0 + 8.0;
  }

}
