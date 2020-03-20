/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/20/20 3:17 PM
 * Last modified 3/20/20 3:12 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GamePlay.Extensions

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.text.Html
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import net.geeksempire.physics.animation.Core.SimpleSpringListener
import net.geeksempire.physics.animation.Core.Spring
import net.geeksempire.physics.animation.Core.SpringConfig
import net.geeksempire.physics.animation.SpringSystem
import net.geeksempire.primepuzzles.GameData.GameInformationVariable
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.GamePlay.Ads.AdsLoading
import net.geeksempire.primepuzzles.GamePlay.GamePlay
import net.geeksempire.primepuzzles.R

fun GamePlay.createSnackbar() : Snackbar {

    val snackbarHint = Snackbar.make(
        gamePlayViewBinding.fullGamePlay,
        Html.fromHtml(
            GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT,
            Html.FROM_HTML_MODE_LEGACY
        ),
        Snackbar.LENGTH_INDEFINITE
    )
    snackbarHint.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackbarHint.setTextColor(ColorStateList.valueOf(getColor(R.color.light)))
    snackbarHint.setActionTextColor(getColor(R.color.yellow))

    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.BOTTOM_TOP,
        intArrayOf(
            getColor(R.color.dark),
            getColor(R.color.darker),
            getColor(R.color.dark)
        )
    )

    val viewSnackbar = snackbarHint.view
    viewSnackbar.background = gradientDrawable

    val snackButton: Button = snackbarHint.getView().findViewById<Button>(R.id.snackbar_action)
    snackButton.setBackgroundColor(Color.TRANSPARENT)

    return snackbarHint
}

fun GamePlay.observeSnackbarToggle() {

    val snackbarHint = createSnackbar()

    GameVariablesViewModel.TOGGLE_SNACKBAR.observe(this@observeSnackbarToggle,
        Observer<Boolean> { newToggleSnackBar ->

            if (newToggleSnackBar!!) {

                snackbarHint.setText(Html.fromHtml(GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT, Html.FROM_HTML_MODE_LEGACY))
                snackbarHint.setAction(GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT) {

                    when (GameInformationVariable.snackBarAction) {
                        GameInformationVariable.HINT_ACTION -> {
                            valueAnimatorProgressBar.pause()
                            countDownTimer.pause()

                            val hintData: String =
                                GameOperations(applicationContext).generateHint()
                            gamePlayViewBinding.gamePlayHintViewInclude.hintEquation.text = hintData

                            val hintAnimation = AnimationUtils.loadAnimation(
                                applicationContext,
                                android.R.anim.fade_in
                            )
                            gamePlayViewBinding.gamePlayHintViewInclude.root.startAnimation(hintAnimation)
                            hintAnimation.setAnimationListener(object :
                                Animation.AnimationListener {
                                override fun onAnimationRepeat(animation: Animation?) {

                                }

                                override fun onAnimationEnd(animation: Animation?) {
                                    gamePlayViewBinding.gamePlayHintViewInclude.root.visibility = View.VISIBLE

                                    val hintBackgroundDim = ValueAnimator.ofArgb(
                                        Color.TRANSPARENT,
                                        getColor(R.color.dark_transparent)
                                    )
                                    hintBackgroundDim.duration = 321
                                    hintBackgroundDim.addUpdateListener { animator ->
                                        //(animator.animatedValue as Int)
                                        gamePlayViewBinding.gamePlayHintViewInclude.root.setBackgroundColor((animator.animatedValue as Int))
                                    }
                                    hintBackgroundDim.start()
                                }

                                override fun onAnimationStart(animation: Animation?) {

                                }
                            })

                            gamePlayViewBinding.gamePlayHintViewInclude.root.setOnClickListener {
                                if (gamePlayViewBinding.gamePlayHintViewInclude.root.isShown) {
                                    val hintAnimationHide =
                                        AnimationUtils.loadAnimation(
                                            applicationContext,
                                            android.R.anim.fade_out
                                        )
                                    gamePlayViewBinding.gamePlayHintViewInclude.root.startAnimation(hintAnimationHide)
                                    hintAnimationHide.setAnimationListener(object :
                                        Animation.AnimationListener {
                                        override fun onAnimationRepeat(animation: Animation?) {

                                        }

                                        override fun onAnimationEnd(animation: Animation?) {
                                            gamePlayViewBinding.gamePlayHintViewInclude.root.visibility = View.INVISIBLE
                                            gamePlayViewBinding.gamePlayHintViewInclude.root.setBackgroundColor(Color.TRANSPARENT)
                                        }

                                        override fun onAnimationStart(animation: Animation?) {

                                        }
                                    })

                                    valueAnimatorProgressBar.resume()
                                    countDownTimer.resume()
                                }
                            }

                            gamePlayViewBinding.gamePlayHintViewInclude.hintGotIt.setOnClickListener {
                                if (gamePlayViewBinding.gamePlayHintViewInclude.root.isShown) {
                                    val hintAnimationHide =
                                        AnimationUtils.loadAnimation(
                                            applicationContext,
                                            android.R.anim.fade_out
                                        )
                                    gamePlayViewBinding.gamePlayHintViewInclude.root.startAnimation(hintAnimationHide)
                                    hintAnimationHide.setAnimationListener(object :
                                        Animation.AnimationListener {
                                        override fun onAnimationRepeat(animation: Animation?) {

                                        }

                                        override fun onAnimationEnd(animation: Animation?) {
                                            gamePlayViewBinding.gamePlayHintViewInclude.root.visibility = View.INVISIBLE
                                            gamePlayViewBinding.gamePlayHintViewInclude.root.setBackgroundColor(Color.TRANSPARENT)
                                        }

                                        override fun onAnimationStart(animation: Animation?) {

                                        }
                                    })

                                    valueAnimatorProgressBar.resume()
                                    countDownTimer.resume()
                                }
                            }
                        }
                        GameInformationVariable.PRIME_NUMBER_ACTION -> {
                            functionsClassUI.circularHideAnimationPrimeNumber(
                                countDownTimer,
                                gamePlayViewBinding.gamePlayPrimeNumberDetectedViewInclude.root,
                                gamePlayViewBinding.primeNumbers.y + (gamePlayViewBinding.primeNumbers.height / 2),
                                gamePlayViewBinding.primeNumbers.x + (gamePlayViewBinding.primeNumbers.width / 2),
                                1f
                            )
                        }
                    }
                }
                Handler().postDelayed({ snackbarHint.show() }, 321)
            } else {
                snackbarHint.dismiss()
            }
        })
}

/**
 *
 * Sides Random Values Functions
 *
 */
fun GamePlay.setupViews() {

    functionsClassUI.shadowValueAnimatorLoop(
        gamePlayViewBinding.primeNumbers,
        19, 3,
        1333, 777,
        getColor(R.color.lighter), 0f, 0f
    )

    val springSystem = SpringSystem.create()

    val springRandomTop = springSystem.createSpring()
    val springRandomLeft = springSystem.createSpring()
    val springRandomRight = springSystem.createSpring()

    springRandomTop.addListener(object : SimpleSpringListener(/*spring*/) {
        override fun onSpringUpdate(spring: Spring?) {
            val value = spring!!.currentValue.toFloat()
            val scale = 1f - (value * 0.5f)

            gamePlayViewBinding.gamePlayControlViewInclude.randomTop.scaleX = scale
            gamePlayViewBinding.gamePlayControlViewInclude.randomTop.scaleY = scale
        }

        override fun onSpringEndStateChange(spring: Spring?) {

        }

        override fun onSpringAtRest(spring: Spring?) {

        }

        override fun onSpringActivate(spring: Spring?) {

        }

    })

    springRandomLeft.addListener(object : SimpleSpringListener(/*spring*/) {
        override fun onSpringUpdate(spring: Spring?) {
            val value = spring!!.currentValue.toFloat()
            val scale = 1f - (value * 0.5f)

            gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.scaleX = scale
            gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.scaleY = scale
        }

        override fun onSpringEndStateChange(spring: Spring?) {

        }

        override fun onSpringAtRest(spring: Spring?) {

        }

        override fun onSpringActivate(spring: Spring?) {

        }

    })

    springRandomRight.addListener(object : SimpleSpringListener(/*spring*/) {
        override fun onSpringUpdate(spring: Spring?) {
            val value = spring!!.currentValue.toFloat()
            val scale = 1f - (value * 0.5f)

            gamePlayViewBinding.gamePlayControlViewInclude.randomRight.scaleX = scale
            gamePlayViewBinding.gamePlayControlViewInclude.randomRight.scaleY = scale
        }

        override fun onSpringEndStateChange(spring: Spring?) {

        }

        override fun onSpringAtRest(spring: Spring?) {

        }

        override fun onSpringActivate(spring: Spring?) {

        }

    })

    val springConfig = SpringConfig(800.0, 20.0 /*friction*/)

    springRandomTop.springConfig = springConfig
    springRandomLeft.springConfig = springConfig
    springRandomRight.springConfig = springConfig


    gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setOnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                springRandomTop.endValue = (0.7)

            }
            MotionEvent.ACTION_UP -> {
                springRandomTop.endValue = (0.0)

            }
        }
        return@setOnTouchListener false
    }
    gamePlayViewBinding.gamePlayControlViewInclude.randomTop.setOnClickListener {}

    gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setOnClickListener { }
    gamePlayViewBinding.gamePlayControlViewInclude.randomLeft.setOnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                springRandomLeft.endValue = (0.7)

            }
            MotionEvent.ACTION_UP -> {
                springRandomLeft.endValue = (0.0)

            }
        }
        return@setOnTouchListener false
    }

    gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setOnClickListener { }
    gamePlayViewBinding.gamePlayControlViewInclude.randomRight.setOnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                springRandomRight.endValue = (0.7)

            }
            MotionEvent.ACTION_UP -> {
                springRandomRight.endValue = (0.0)

            }
        }
        return@setOnTouchListener false
    }
}

fun GamePlay.setupAdsView() {
    val adViewLayoutParams = gamePlayViewBinding.adViews.layoutParams as RelativeLayout.LayoutParams
    val statusBarHeight = if (intent.getIntExtra("StatusBarHeight", functionsClassUI.DpToInteger(24f).toInt()) == 0) {
        intent.getIntExtra("StatusBarHeight", functionsClassUI.DpToInteger(24f).toInt())
    } else {
        functionsClassUI.DpToInteger(24f).toInt()
    }
    adViewLayoutParams.height = adViewLayoutParams.height + statusBarHeight
    gamePlayViewBinding.adViews.setPadding(0, statusBarHeight, 0, 0)
    gamePlayViewBinding.adViews.layoutParams = adViewLayoutParams

    AdsLoading(applicationContext)
        .initializeAds(gamePlayViewBinding.adViewBannerGamePlay)
}

fun GamePlay.setupGameControllerViews() {
    val layoutParams = gamePlayViewBinding.gamePlayControlViewInclude.root.layoutParams
    layoutParams.height = functionsClassUI.displayX()
    gamePlayViewBinding.gamePlayControlViewInclude.root.layoutParams = layoutParams
}