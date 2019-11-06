package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.animation.Animator
import android.content.Context
import android.os.Handler
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import net.geeksempire.primepuzzles.GameLogic.GameVariables
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

    fun circularRevealAnimation(viewToReveal: View, yPosition: Float, xPosition: Float, startRadius: Float) {

        val finalRadius = hypot(displayX().toDouble(), displayY().toDouble()).toInt()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            viewToReveal,
            xPosition.toInt(),
            yPosition.toInt(),
            DpToInteger(startRadius),
            finalRadius.toFloat()
        )
        circularReveal.duration = 1321
        circularReveal.interpolator = AccelerateInterpolator(3.0f)

        viewToReveal.visibility = View.VISIBLE
        circularReveal.start()
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                Handler().postDelayed({
                    GameVariables.TOGGLE_SNACKBAR.value = false

                    circularHideAnimation(
                        viewToReveal,
                        yPosition,
                        xPosition,
                        1f
                    )
                }, 1321)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }

    fun circularHideAnimation(viewToReveal: View, yPosition: Float, xPosition: Float, startRadius: Float) {

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
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }
}