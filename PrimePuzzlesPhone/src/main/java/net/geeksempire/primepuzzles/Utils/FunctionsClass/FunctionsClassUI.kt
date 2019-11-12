/*
 * Copyright © 2019 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/11/19 6:49 PM
 * Last modified 11/11/19 6:48 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import net.geeksempire.primepuzzles.GameInformation.GameVariables
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import kotlin.math.hypot

class FunctionsClassUI(initContext: Context) {

    private val context: Context = initContext

    fun displayX(): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun displayY(): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun DpToPixel(dp: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return dp * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun DpToInteger(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }

    fun circularRevealAnimationPrimeNumber(viewToReveal: View, yPosition: Float, xPosition: Float, startRadius: Float) {
        GamePlay.countDownTimer.pause()

        val finalRadius = hypot(displayX().toDouble(), displayY().toDouble()).toInt()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            viewToReveal,
            xPosition.toInt(),
            yPosition.toInt(),
            DpToInteger(startRadius),
            finalRadius.toFloat()
        )
        circularReveal.duration = 1531
        circularReveal.interpolator = AccelerateInterpolator(1.0f)

        viewToReveal.visibility = View.VISIBLE
        circularReveal.start()
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                Handler().postDelayed({
                    GameVariables.TOGGLE_SNACKBAR.value = false

                    circularHideAnimationPrimeNumber(
                        viewToReveal,
                        yPosition,
                        xPosition,
                        1f
                    )
                }, 1111)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }

    fun circularHideAnimationPrimeNumber(viewToReveal: View, yPosition: Float, xPosition: Float, startRadius: Float) {

        val finalRadius = hypot(displayX().toDouble(), displayY().toDouble()).toInt()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            viewToReveal,
            xPosition.toInt(),
            yPosition.toInt(),
            finalRadius.toFloat(),
            DpToInteger(startRadius)
        )
        circularReveal.duration = 999
        circularReveal.interpolator = AccelerateInterpolator(3.0f)

        circularReveal.start()
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                viewToReveal.visibility = View.INVISIBLE

                GamePlay.countDownTimer.resume()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }

    fun shadowValueAnimatorLoop(view: TextView,
                                startValue: Int, endValue: Int,
                                startDuration: Int, endDuration: Int,
                                shadowColor: Int, shadowX: Float, shadowY: Float) {
        val primeNumbersGlowDown =
            ValueAnimator.ofInt(
                startValue,
                endValue
            )
        primeNumbersGlowDown.startDelay = startDuration.toLong()
        primeNumbersGlowDown.duration = startDuration.toLong()
        primeNumbersGlowDown.addUpdateListener { animator ->
            //(animator.animatedValue as Int)

            view.setShadowLayer((animator.animatedValue as Int).toFloat(), shadowX, shadowY, shadowColor)
        }
        primeNumbersGlowDown.start()
        primeNumbersGlowDown.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                val primeNumbersGlowUp =
                    ValueAnimator.ofInt(
                        endValue,
                        startValue
                    )
                primeNumbersGlowUp.duration = endDuration.toLong()
                primeNumbersGlowUp.addUpdateListener { animator ->
                    //(animator.animatedValue as Int)

                    view.setShadowLayer((animator.animatedValue as Int).toFloat(), shadowX, shadowY,  shadowColor)
                }
                primeNumbersGlowUp.start()
                primeNumbersGlowUp.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        shadowValueAnimatorLoop(view, startValue, endValue, startDuration, endDuration, shadowColor, shadowX, shadowY)
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }
                })
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }
}