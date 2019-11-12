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

public class SpringUtil {

  /**
   * Map a value within a given range to another range.
   * @param value the value to map
   * @param fromLow the low end of the range the value is within
   * @param fromHigh the high end of the range the value is within
   * @param toLow the low end of the range to map to
   * @param toHigh the high end of the range to map to
   * @return the mapped value
   */
  public static double mapValueFromRangeToRange(
      double value,
      double fromLow,
      double fromHigh,
      double toLow,
      double toHigh) {
    double fromRangeSize = fromHigh - fromLow;
    double toRangeSize = toHigh - toLow;
    double valueScale = (value - fromLow) / fromRangeSize;
    return toLow + (valueScale * toRangeSize);
  }

  /**
   * Clamp a value to be within the provided range.
   * @param value the value to clamp
   * @param low the low end of the range
   * @param high the high end of the range
   * @return the clamped value
   */
  public static double clamp(double value, double low, double high) {
    return Math.min(Math.max(value, low), high);
  }
}

